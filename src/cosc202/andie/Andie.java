package cosc202.andie;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE)
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    /** getting the Language Preference bundle ANDIE is currently running on. */
    static ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Image alterations apply a per-pixel operation to the image
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        // Actions that affect the representation of colour in the image
        
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that use the mouse input
        MouseActions mouseActions = new MouseActions();
        menuBar.add(mouseActions.createMenu());

        // Actions that change the language
        LanguageActions languageActions = new LanguageActions();
        menuBar.add(languageActions.createMenu());

        // Add in the toolbar for easy action to frequently used actions.
        JToolBar toolBar = new JToolBar();
        JButton button = null;

        MacroActions MacroActions = new MacroActions();
        menuBar.add(MacroActions.createMenu());



        // Frequently used actions from the File menu being added here.
        FileActions FileAction = new FileActions();
        button = new JButton(
                FileAction.new FileExitAction("\u274C", null, bundle.getString("Exit_The_Program") + " (Alt X)",
                        Integer.valueOf(KeyEvent.VK_X)));
        toolBar.add(button);
        button = new JButton(
                FileAction.new FileOpenAction("\uD83D\uDCC2", null, bundle.getString("Open_A_File") + " (Alt O)",
                        Integer.valueOf(KeyEvent.VK_O)));
        toolBar.add(button);
        button = new JButton(
                FileAction.new FileSaveAction("\uD83D\uDDAB", null, bundle.getString("Save_The_File") + " (Alt S)",
                        Integer.valueOf(KeyEvent.VK_S)));
        toolBar.add(button);
        button = new JButton(
                FileAction.new FileSaveAsAction("\uD83D\uDCBE", null, bundle.getString("Save_A_Copy") + " (Alt A)",
                        Integer.valueOf(KeyEvent.VK_A)));
        toolBar.add(button);

        // Frequently used actions from the Edit menu being added here.
        EditActions EditAction = new EditActions();
        button = new JButton(EditAction.new UndoAction("\uD83E\uDC28", null, bundle.getString("Undo") + " (Alt Z)",
                Integer.valueOf(KeyEvent.VK_Z)));
        toolBar.add(button);
        button = new JButton(EditAction.new RedoAction("\uD83E\uDC26", null, bundle.getString("Redo") + " (Alt Y)",
                Integer.valueOf(KeyEvent.VK_Y)));
        toolBar.add(button);

        // Frequently used actions from the Colour menu being added here.
        ColourActions ColourAction = new ColourActions();
        button = new JButton(ColourAction.new BrightnessAdjustAction("\uD83D\uDD06", null,
                bundle.getString("Adjust_The_Brightness") + " (Alt B)", Integer.valueOf(KeyEvent.VK_B)));
        toolBar.add(button);
        button = new JButton(ColourAction.new ConvertToGreyAction("\u26AB", null,
                bundle.getString("Convert_To_Greyscale") + " (Alt G)", Integer.valueOf(KeyEvent.VK_G)));
        toolBar.add(button);
        button = new JButton(ColourAction.new ContrastAdjustAction("\uD83C\uDF17", null,
                bundle.getString("Adjust_The_Contrast") + " (Alt C)", Integer.valueOf(KeyEvent.VK_C)));
        toolBar.add(button);

        // Frequently used actions from the Filter menu being added here.
        FilterActions FilterAction = new FilterActions();
        button = new JButton(FilterAction.new SharpenBlurAction("\u266F", null,
                bundle.getString("Apply_A_Sharpen_Blur") + " (Alt J)", Integer.valueOf(KeyEvent.VK_J)));
        toolBar.add(button);
        button = new JButton(
                FilterAction.new MeanFilterAction("\u00B5", null, bundle.getString("Apply_A_Mean_Filter") + " (Alt M)",
                        Integer.valueOf(KeyEvent.VK_M)));
        toolBar.add(button);

        // Frequently used actions from the Transform menu being added here.
        TransformActions TransformAction = new TransformActions();
        button = new JButton(
                TransformAction.new RotateAction("\u2B8C", null, bundle.getString("Apply_A_Rotation") + " (Alt R)",
                        Integer.valueOf(KeyEvent.VK_R)));
        toolBar.add(button);
        button = new JButton(
                TransformAction.new FlipAction("\uD83D\uDDD8", null, bundle.getString("Apply_A_Flip") + " (Alt F)",
                        Integer.valueOf(KeyEvent.VK_F)));
        toolBar.add(button);

        // Frequently used actions from the View menu being added here.
        ViewActions ViewAction = new ViewActions();
        button = new JButton(ViewAction.new ZoomInAction("+", null, bundle.getString("Zoom_In") + " (Alt PLUS)",
                Integer.valueOf(KeyEvent.VK_PLUS)));
        toolBar.add(button);
        button = new JButton(ViewAction.new ZoomOutAction("\u2212", null, bundle.getString("Zoom_Out") + " (Alt MINUS)",
                Integer.valueOf(KeyEvent.VK_MINUS)));
        toolBar.add(button);

        button = new JButton(
                ViewAction.new ZoomFullAction("\uD83D\uDD0D", null, bundle.getString("Zoom_Full") + " (Alt 1)",
                        Integer.valueOf(KeyEvent.VK_1)));
        toolBar.add(button);

        // Frequently used actions from the Macro menu being added here.
        MacroActions MacroAction = new MacroActions();
        button = new JButton(MacroAction.new MacroStartAction("\u25B6", null, bundle.getString("Start_Recording") + " (Alt D)",
                        Integer.valueOf(KeyEvent.VK_D)));
        toolBar.add(button);
        button = new JButton(MacroAction.new MacroStopAction("\u23F9", null, bundle.getString("Stop_Recording") + " (Alt E)",
                Integer.valueOf(KeyEvent.VK_E)));
        toolBar.add(button);

        frame.add(toolBar, BorderLayout.NORTH);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
        
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
