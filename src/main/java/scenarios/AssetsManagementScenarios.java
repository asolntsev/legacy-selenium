package scenarios;

import base.BaseComponent;
import entities.Assets;
import helpers.Environment;
import helpers.Pauses;
import helpers.StringManager;
import helpers.Timeouts;
import pages.assetsmanagement.AssetsPage;
import pages.dialogs.DeleteDialogPage;
import pages.dialogs.UploadAssetsPage;

public class AssetsManagementScenarios extends BaseComponent {
    
    public static String uploadFileToFolder(AssetsPage assetsPage, Assets file) {
        return uploadFileToFolder(assetsPage, file, true);
    }
    
    public static String uploadFileToFolder(AssetsPage assetsPage, Assets file, boolean rename) {
        String filename = rename ? StringManager.getRandomAlphabetic() + file.getExtension() : file.getFullName();
        assetsPage.uploadFile(file);
        Pauses.sleep(Timeouts.SLOW);
        
        UploadAssetsPage uploadAssetsPage = new UploadAssetsPage();
        uploadAssetsPage.renameFile(filename);
        uploadAssetsPage.clickUploadButton();
        
        Environment.waitForJs();
        
        return filename;
    }
    
    public static void verifyFileExists(AssetsPage assetsPage, String fullFileName) {
        assetsPage.getFileTitleLabel(fullFileName).assertExists(Timeouts.SLOW);
    }
    
    public static void verifyFileDoesNotExist(AssetsPage assetsPage, String fullFileName) {
        assetsPage.getFileTitleLabel(fullFileName).assertDoesNotExist(Timeouts.SLOW);
    }
    
    public static void deleteFile(AssetsPage assetsPage, String fullFileName) {
        assetsPage.selectFile(fullFileName);
        assetsPage.clickTopMenuDeleteButton();
        
        new DeleteDialogPage().clickDeleteButton();
    }
    
}




