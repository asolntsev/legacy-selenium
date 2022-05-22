package helpers;

import builders.PageBuilder;
import entities.Assets;
import entities.Components;
import entities.Page;
import entities.User;
import entities.components.AdvancedTeaserEntity;
import entities.components.SimpleTeaserEntity;
import entities.enums.ContentItemsAssets;
import entities.enums.ContentItemsPages;
import entities.enums.PageTemplates;
import logging.Log;
import utils.HTTPUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiHelper extends HTTPUtils {
    
    private static final Pattern LOGIN_TOKEN_PATTERN = Pattern.compile("(login-token=.*);.Path");
    private static final Pattern CSRF_TOKEN_PATTERN = Pattern.compile("token\":\"(.*)\"}");
    private static final Pattern PAGE_TITLE_PATTERN = Pattern.compile("itemprop=\"title\" title=\"([A-z0-9]*)\">");
    
    private static final String FILE_FOLDER = "files";
    
    private static final String TITLE_CODE = "./jcr:title";
    private static final String NAME_CODE = "pageName";
    private static final String USERNAME_CODE = "j_username";
    private static final String PASSWORD_CODE = "j_password";
    private static final String VALIDATE_CODE = "j_validate";
    private static final String CHARSET_CODE = "_charset_";
    private static final String PARENT_PATH_CODE = "parentPath";
    private static final String TEMPLATE_CODE = "template";
    private static final String HEADER_COOKIE = "Cookie";
    private static final String HEADER_CSRF_TOKEN = "CSRF-Token";
    private static final String HEADER_REFERER_TOKEN = "Referer";
    private static final String CHECK_CHILDREN_CODE = "checkChildren";
    private static final String COMMAND_CODE = "cmd";
    private static final String FORCE_CODE = "force";
    private static final String PATH_CODE = "path";
    private static final String NAME_HINT_CODE = ":nameHint";
    private static final String RESOURCE_TYPE_CODE = "./sling:resourceType";
    private static final String RESOURCE_TYPE_TEMPLATE = "huge-cms/components/content/%1$s";
    private static final String PARENT_RESOURCE_TYPE_CODE = "parentResourceType";
    private static final String PARENT_RESOURCE_TYPE_VALUE = "huge-cms/components/structure/responsivegrid";
    private static final String EXPERIENCE_FRAGMENT_TEMPLATE_CODE = "variantTemplate";
    private static final String EXPERIENCE_FRAGMENT_TITLE_CODE = "variantTitle";
    private static final String PAGE_TITLE_CODE = "pageTitle";
    
    private static String loginToken;
    private static String csrfToken;
    
    public static void createNewPage(Page page) {
        loginViaApi();
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        
        Map<String, String> bodyParameters = generatePageObjectForUpload(page);
        
        try {
            HttpURLConnection response = HTTPUtils.executePostRequest(Environment.getCreatePageUrl(), bodyParameters,
                    headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            Verifications.assertTrue(response.getResponseCode() == 200 &&
                            responseMessage.contains("id=\"Message\">Page created"),
                    String.format("Failed to create a page. Response message: %1$s", responseMessage));
            Log.logInfo("Page '" + page.getTitle() + "' was created");
        } catch (IOException e) {
            Log.logFailWithoutScreenshot("Failed to create new page");
            e.printStackTrace();
        }
    }
    
    public static Page createNewPageWithRandomTitle(PageTemplates template) {
        String pageTitle = StringManager.getRandomAlphanumeric();
        String pageName = pageTitle.toLowerCase();
        
        Page page = PageBuilder
                .newPage()
                .withName(pageName)
                .withTitle(pageTitle)
                .withParentPath(NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS))
                .withTemplatePath(template.getPath())
                .build();
        
        createNewPage(page);
        
        return page;
    }
    
    private static Map<String, String> generatePageObjectForUpload(Page page) {
        Map<String, String> pageParameters = new LinkedHashMap<>();
        pageParameters.put(TITLE_CODE, page.getTitle());
        pageParameters.put(NAME_CODE, page.getName());
        pageParameters.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        pageParameters.put(PARENT_PATH_CODE, page.getParentPath());
        pageParameters.put(TEMPLATE_CODE, page.getTemplatePath());
        
        return pageParameters;
    }
    
    private static void deleteContent(String contentPath, String contentName) {
        loginViaApi();
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        headers.put(HEADER_REFERER_TOKEN, contentPath);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        params.put(CHECK_CHILDREN_CODE, "true");
        params.put(COMMAND_CODE, "deletePage");
        params.put(FORCE_CODE, "true");
        params.put(PATH_CODE, contentPath + StringManager.URL_SEPARATOR + contentName);
        
        try {
            HttpURLConnection response = HTTPUtils.executePostRequest(Environment.getDeletePageUrl(), params, headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            Verifications.assertTrue(response.getResponseCode() == 200 &&
                            responseMessage.contains("id=\"Message\">Deleted"),
                    String.format("Failed to delete a content item. Response message: %1$s", responseMessage));
            Log.logInfo("Content item was deleted");
        } catch (IOException e) {
            Log.logFailWithoutScreenshot("Failed to delete content");
            e.printStackTrace();
        }
    }
    
    public static void deleteFile(String fullFileName) {
        deleteContent(NavigationHelper.getPathToContentItem(ContentItemsAssets.TEST_AUTOMATION), fullFileName);
        Log.logInfo("File " + fullFileName + " was deleted.");
    }
    
    public static void deletePage(String pageName) {
        deleteContent(NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS), pageName);
        Log.logInfo("Page " + pageName + " was deleted.");
    }
    
    public static Components addComponentsToPage(String pageName, Components component) {
        String path = getUrlForComponentCreation(ContentItemsPages.AUTOMATION_TESTS, pageName);
        String random = StringManager.getRandomNumericShort();
        String referer = Environment.getEditorsPageUrl(Environment.getApiUrl(), pageName);
        
        loginViaApi();
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        headers.put(HEADER_REFERER_TOKEN, referer);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        params.put(RESOURCE_TYPE_CODE, String.format(RESOURCE_TYPE_TEMPLATE, component.getValue()));
        params.put(PARENT_RESOURCE_TYPE_CODE, PARENT_RESOURCE_TYPE_VALUE);
        params.put(NAME_HINT_CODE, component.getValue() + random);
        
        try {
            HttpURLConnection response = HTTPUtils.executePostRequest(path, params, headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            Verifications.assertTrue(response.getResponseCode() == 201 &&
                            responseMessage.contains("Content created"),
                    String.format("Failed to create component. Response message: %1$s", responseMessage));
            Log.logInfo("Component [" + component.getName() + "] was created");
        } catch (Exception e) {
            Log.logFailWithoutScreenshot("Failed to create a component");
            e.printStackTrace();
        }
        
        return new Components().setDataPath(component.getDataPath() + random)
                .setName(component.getName())
                .setValue(component.getValue());
    }
    
    private static String getUrlForComponentCreation(ContentItemsPages contentItem, String pageName) {
        return Environment.getApiUrl() + NavigationHelper.getPathToContentItem(contentItem)
                + StringManager.URL_SEPARATOR + pageName + "/jcr:content/responsivegrid/";
    }
    
    private static void loginViaApi() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        params.put(USERNAME_CODE, User.ADMINISTRATOR.getUserName());
        params.put(PASSWORD_CODE, User.ADMINISTRATOR.getPassword());
        params.put(VALIDATE_CODE, "true");
        
        try {
            loginToken = getLoginToken(HTTPUtils.executePostRequest(Environment.getLoginUrl(), params));
            
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put(HEADER_COOKIE, loginToken);
            
            csrfToken = getCsrfToken(HTTPUtils.executeGetRequest(Environment.getTokenUrl(), headers));
            
        } catch (IOException e) {
            Log.logFailWithoutScreenshot("Failed to execute login request");
        }
    }
    
    private static String getLoginToken(HttpURLConnection entity) {
        return getRegexValue(LOGIN_TOKEN_PATTERN, entity.getHeaderField("Set-Cookie"));
    }
    
    private static String getCsrfToken(HttpURLConnection entity) {
        return getRegexValue(CSRF_TOKEN_PATTERN, HTTPUtils.getResponseBody(entity));
    }
    
    private static String getRegexValue(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }
    
    public static String uploadFile(Assets asset) {
        Assets assetForUpload = new Assets();
        assetForUpload.setName(asset.getName());
        assetForUpload.setContentType(asset.getContentType());
        assetForUpload.setExtension(asset.getExtension());
        assetForUpload.setPath(System.getProperty("user.dir") + File.separator + FILE_FOLDER + File.separator + asset.getFullName());
        
        try {
            loginViaApi();
            
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put(HEADER_COOKIE, loginToken);
            headers.put(HEADER_CSRF_TOKEN, csrfToken);
            
            return HTTPUtils.uploadFile(assetForUpload, headers);
        } catch (Exception e) {
            e.printStackTrace();
            Log.logFail("Failed to create file. " + e.toString());
            return null;
        }
    }
    
    public static void updateAdvancedTeaser(AdvancedTeaserEntity advancedTeaser, Components component, Page page) {
        loginViaApi();
        
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("./disclaimerText", advancedTeaser.getDisclaimerText());
        params.put("./heading", advancedTeaser.getHeading());
        params.put("./headingHtml", advancedTeaser.getHeading());
        params.put("./mainText", advancedTeaser.getMainText());
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        
        executeUpdateComponentRequestAndVerify(component, page, headers, params);
    }
    
    public static void updateSimpleTeaser(SimpleTeaserEntity simpleTeaser, Components component, Page page) {
        loginViaApi();
        
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("./heading", simpleTeaser.getHeading());
        params.put("./headingHtml", simpleTeaser.getHeading());
        params.put("./mainText", simpleTeaser.getMainText());
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        
        executeUpdateComponentRequestAndVerify(component, page, headers, params);
    }
    
    private static void executeUpdateComponentRequestAndVerify(Components component, Page page, Map<String, String> headers, Map<String, String> params) {
        String url = Environment.getComponentUpdateUrl(component, page);
        try {
            HttpURLConnection response = HTTPUtils.executePostRequest(url, params, headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            Verifications.assertTrue(response.getResponseCode() == 200 &&
                            responseMessage.contains("Content modified"),
                    String.format("Failed to update component. Response message: %1$s", responseMessage));
        } catch (Exception e) {
            Log.logFailWithoutScreenshot("Failed to update a component");
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> getListOfChildPages(ContentItemsPages contentItemsPage) {
        loginViaApi();
        
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        
        try {
            HttpURLConnection response = HTTPUtils.executeGetRequest(Environment.getContentItemPageUrl(contentItemsPage), headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            return getRegexValues(responseMessage, PAGE_TITLE_PATTERN);
        } catch (Exception e) {
            Log.logInfo("Failed to execute request. " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private static ArrayList<String> getRegexValues(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line.toLowerCase());
        ArrayList<String> values = new ArrayList<>();
        
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                values.add(matcher.group(i));
            }
        }
        
        return values;
    }
    
    public static void publishPage(String pageName) {
        loginViaApi();
        
        String pathValue = NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS)
                + StringManager.URL_SEPARATOR + pageName;
        
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HEADER_COOKIE, loginToken);
        headers.put(HEADER_CSRF_TOKEN, csrfToken);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put(CHARSET_CODE, HTTPUtils.DEFAULT_ENCODING.toLowerCase());
        params.put(COMMAND_CODE, "Activate");
        params.put(PATH_CODE, pathValue);
        
        try {
            HttpURLConnection response = HTTPUtils.executePostRequest(Environment.getPublishPageUrl(), params, headers);
            String responseMessage = HTTPUtils.getResponseBody(response);
            String expected = "Replication started for " + pathValue;
            
            Verifications.assertContains(responseMessage, expected, "Response message expected to contain '"
                    + expected + "', but it was '" + responseMessage + "'");
        } catch (Exception e) {
            Log.logInfo("Failed to publish the page. " + e.getMessage());
        }
    }
}
