package assets;

import base.AbstractTest;
import entities.Assets;
import entities.Page;
import entities.User;
import entities.enums.AssetTypes;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import helpers.NavigationHelper;
import org.junit.jupiter.api.Test;
import pages.assetsmanagement.AssetsPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.SidePanelPage;
import scenarios.AssetsManagementScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import scenarios.SidePanelScenarios;

import java.util.ArrayList;

/**
 * Test for adding file
 */
class AddFilesToUserProfileTest extends AbstractTest {
    private static final Assets FILE_JPG = Assets.FILE_JPG;
    
    @Test
    void verifyFilesUploadViaUi() {
        Page page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
        AssetsPage assetsPage = NavigationHelper.navigateToAutomationTestsAssetsFolder();
        
        String fileName = AssetsManagementScenarios.uploadFileToFolder(assetsPage, FILE_JPG);
        AssetsManagementScenarios.verifyFileExists(assetsPage, fileName);
        
        EditorsViewPage editorsViewPage = PageScenarios.openPageInAuthorMode(page);
        SidePanelPage sidePanel = SidePanelScenarios.openPanelAndPerformSearchForFileOnAuthorPart(editorsViewPage, fileName, AssetTypes.IMAGES);
        SidePanelScenarios.verifyResultFound(sidePanel, fileName);
        
        assetsPage = NavigationHelper.navigateToAutomationTestsAssetsFolder();
        AssetsManagementScenarios.deleteFile(assetsPage, fileName);
        AssetsManagementScenarios.verifyFileDoesNotExist(assetsPage, fileName);
        
        editorsViewPage = PageScenarios.openPageInAuthorMode(page);
        sidePanel = SidePanelScenarios.openPanelAndPerformSearchForFileOnAuthorPart(editorsViewPage, fileName, AssetTypes.IMAGES);
        SidePanelScenarios.verifyResultNotFound(sidePanel, fileName);
        
        ApiHelper.deletePage(page.getName());
    }
    
    @Test
    void verifyFilesUploadViaApi() {
        ArrayList<Assets> listOfFiles = new ArrayList<>();
        listOfFiles.add(Assets.FILE_DOC);
        listOfFiles.add(Assets.FILE_GIF);
        listOfFiles.add(Assets.FILE_PNG);
        listOfFiles.add(Assets.FILE_PPTX);
        listOfFiles.add(Assets.FILE_XLSX);
        listOfFiles.add(Assets.FILE_XML);
        
        ArrayList<String> listOfNewFileNames = new ArrayList<>();
        listOfFiles.forEach(file -> {
            listOfNewFileNames.add(ApiHelper.uploadFile(file));
        });
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
        AssetsPage assetsPage = NavigationHelper.navigateToAutomationTestsAssetsFolder();
        
        listOfNewFileNames.forEach(file ->
                AssetsManagementScenarios.verifyFileExists(assetsPage, file));
        
        listOfNewFileNames.forEach(ApiHelper::deleteFile);
    }
    
}
