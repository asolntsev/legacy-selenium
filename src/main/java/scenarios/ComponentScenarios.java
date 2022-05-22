package scenarios;

import base.BaseComponent;
import base.BaseElement;
import entities.Components;
import helpers.Environment;
import helpers.Pauses;
import helpers.Timeouts;
import helpers.Verifications;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.components.InsertNewComponentPage;
import pages.dialogs.DeleteDialogPage;
import pages.page.editor.ContentAbstractPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import utils.WebDriverFactory;

public class ComponentScenarios extends BaseComponent {
    
    public static void addNewComponent(EditorsViewPage editorsPage, Components component) {
        editorsPage.switchToEditMode();
        editorsPage.clickOnDragComponentsHere();
        editorsPage.clickOnInsertButton();
        Pauses.sleep();
        
        InsertNewComponentPage insertNewComponentPage = new InsertNewComponentPage();
        insertNewComponentPage.enterKeyword(component.getName());
        insertNewComponentPage.selectResult(component);
    }
    
    public static void deleteComponent(EditorsViewPage editorsPage, Components component) {
        editorsPage.switchToEditMode();
        editorsPage.getComponentContainerInEditView(component).clickViaJS();
        editorsPage.clickOnDeleteButton();
        
        DeleteDialogPage deleteDialogPage = new DeleteDialogPage();
        deleteDialogPage.clickDeleteButton();
        
        Environment.waitForJs();
    }
    
    public static void assertComponentExistsInPreviewMode(EditorsViewPage editorsPage, Components component) {
        Environment.waitForJs();
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        Verifications.assertTrue(previewModePage.getComponentContainerInPreviewMode(component).exists(),
                "Component [" + component.getName() + "] should exist on author environment in preview mode, but was not found");
        previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    public static void assertComponentDoesNotExistInPreviewMode(EditorsViewPage editorsPage, Components component) {
        Environment.waitForJs();
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        
        Verifications.assertFalse(previewModePage.getComponentContainerInPreviewMode(component).exists(),
                "Component [" + component.getName() + "] should NOT exist on author environment in preview mode, but was found");
        previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    public static void assertComponentExistsInEditMode(EditorsViewPage editorsPage, Components component) {
        Environment.waitForJs();
        editorsPage.switchToEditMode();
        Verifications.assertTrue(editorsPage.getComponentContainerInEditView(component).exists(),
                "Component [" + component.getName() + "] should exist on author environment in edit view, but was not found");
    }
    
    public static void assertComponentDoesNotExistInEditMode(EditorsViewPage editorsPage, Components component) {
        Environment.waitForJs();
        editorsPage.switchToEditMode();
        Verifications.assertFalse(editorsPage.getComponentContainerInEditView(component).exists(),
                "Component [" + component.getName() + "] should NOT exist on author environment in edit view, but was found");
    }
    
    public static void assertComponentExistsOnPublishEnvironment(PublishedViewPage publishedViewPage, Components component) {
        Environment.waitForJs();
        
        waitForPublishedPageToBeUpdated(publishedViewPage, component, true);
        
        Verifications.assertTrue(publishedViewPage.getContentContainer(component).exists(),
                "Component [" + component.getName() + "] should exist on publish environment, but was not found");
    }
    
    public static void assertComponentDoesNotExistOnPublishEnvironment(PublishedViewPage publishedViewPage, Components component) {
        Environment.waitForJs();
        
        waitForPublishedPageToBeUpdated(publishedViewPage, component, false);
        
        Verifications.assertFalse(publishedViewPage.getContentContainer(component).exists(),
                "Component [" + component.getName() + "] should NOT exist on publish environment, but was found");
    }
    
    public static void waitForPublishedPageToBeUpdated(PublishedViewPage publishedViewPage, Components component, boolean exists) {
        try {
            new WebDriverWait(WebDriverFactory.instance().getInitializedDriver(), Timeouts.SLOW.getValue()).until(c -> {
                WebDriverFactory.instance().refreshPage();
                return publishedViewPage.getContentContainer(component).exists() == exists;
            });
        } catch (TimeoutException ex) {
        
        }
    }
    
    public static void waitForPublishedPageToBeUpdated(ContentAbstractPage publishedViewPage, BaseElement element, boolean exists) {
        try {
            new WebDriverWait(WebDriverFactory.instance().getInitializedDriver(), Timeouts.SLOW.getValue()).until(c -> {
                WebDriverFactory.instance().refreshPage();
                return element.exists();
            });
        } catch (TimeoutException ex) {
        
        }
    }
    
    public static void openComponentInEditMode(EditorsViewPage editorsPage, Components component) {
        editorsPage.switchToEditMode();
        editorsPage.getComponentContainerInEditView(component).clickViaJS();
        editorsPage.clickOnConfigureButton();
    }
    
}
