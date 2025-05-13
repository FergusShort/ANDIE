package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /** A list of actions for image actions */
    protected ArrayList<ImageAction> imageActions;

    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        // Mean Filter
        actions.add(new MeanFilterAction(bundle.getString("Mean_Filter"), null,
                bundle.getString("Apply_A_Mean_Filter") + " (Alt M)", Integer.valueOf(KeyEvent.VK_M)));
        // Soft Blur
        actions.add(new SoftBlurAction(bundle.getString("Soft_Blur"), null, bundle.getString("Apply_A_Soft_Blur"),
                Integer.valueOf(KeyEvent.VK_B)));
        // Gaussian Blur
        actions.add(new GaussianBlurAction(bundle.getString("Gaussian_Blur"), null,
                bundle.getString("Apply_A_Gaussian_Blur"), Integer.valueOf(KeyEvent.VK_N)));
        // Sharpen Blur
        actions.add(new SharpenBlurAction(bundle.getString("Sharpen_Blur"), null,
                bundle.getString("Apply_A_Sharpen_Blur") + " (Alt J)", Integer.valueOf(KeyEvent.VK_J)));
        // Median Blur
        actions.add(new MedianBlurAction(bundle.getString("Median_Blur"), null, bundle.getString("Apply_A_Median_Blur"),
                Integer.valueOf(KeyEvent.VK_V)));
        // Scatter filter
        actions.add(new RandomScatterAction(bundle.getString("Random_Scatter"), null,
                bundle.getString("Apply_A_Random_Scatter"), Integer.valueOf(KeyEvent.VK_M)));
        // Block average
        actions.add(new BlockAverageAction(bundle.getString("Block_Average"), null,
                bundle.getString("Apply_A_Block_Average"), Integer.valueOf(KeyEvent.VK_M)));
        // Emboss Filter
        actions.add(new EmbossFilterAction(bundle.getString("Emboss_Filter"), null,
                bundle.getString("Apply_An_Emboss_Filter"), Integer.valueOf(KeyEvent.VK_M)));
        // Sobel Filter
        actions.add(new SobelFilterAction(bundle.getString("Sobel_Filter"), null,
                bundle.getString("Apply_A_Sobel_Filter"), Integer.valueOf(KeyEvent.VK_M)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("Filter"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("Enter_Filter_Radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to blur an image with a soft blur filter.
     * </p>
     * 
     * @see SoftBlur
     */
    public class SoftBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new Soft Blur filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            target.getImage().apply(new SoftBlur());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to blur an image with a sharpen blur filter.
     * </p>
     * 
     * @see SharpenBlur
     */
    public class SharpenBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new Soft Blur filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            target.getImage().apply(new SharpenBlur());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to blur an image with a guassian blur filter.
     * </p>
     * 
     * @see GaussianBlur
     */
    public class GaussianBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the gaussian blur action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link GaussianBlur}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("Enter_Filter_Radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new GaussianBlur(radius));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianBlur
     */
    public class MedianBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new median-blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Median blur action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianBlurAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MedianBlur}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("Enter_Filter_Radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new MedianBlur(radius));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see RandomScatter
     */
    public class RandomScatterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RandomScatterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Median blur action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RandomScatterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link RandomScatter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("Enter_Filter_Radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new RandomScatter(radius));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see BlockAverage
     */
    public class BlockAverageAction extends ImageAction {

        /**
         * <p>
         * Create a new median-blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BlockAverageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Median blur action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RandomScatterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link RandomScatter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int x = 1, y = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel xModel = new SpinnerNumberModel(1, 1, 100, 1);
            SpinnerNumberModel yModel = new SpinnerNumberModel(1, 1, 100, 1);

            JSpinner xSpinner = new JSpinner(xModel);
            JSpinner ySpinner = new JSpinner(yModel);

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel(bundle.getString("Enter_x_radius:")));
            panel.add(xSpinner);
            panel.add(new JLabel(bundle.getString("Enter_y_radius:")));
            panel.add(ySpinner);

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("Enter_width_and_height_radii"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                x = xModel.getNumber().intValue();
                y = yModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new BlockAverage(x, y));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to outline an image with an emboss filter.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new emboss-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the emboss-filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for an emboss direction, then applies the appropriate
         * {@link EmbossFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the emboss direction - ask the user.
            int direction = 0; // Default to NONE EmbossFilter.NONE

            // Pop-up dialog box to ask for the emboss direction value.
            String[] options = { bundle.getString("Left"), bundle.getString("Top_left"),
                    bundle.getString("Top"), bundle.getString("Top_right"), bundle.getString("Right"),
                    bundle.getString("Bottom_right"), bundle.getString("Bottom"), bundle.getString("Bottom_left") };
            JComboBox<String> directionComboBox = new JComboBox<>(options);

            // Checkbox for offset
            JCheckBox useOffsetCheckbox = new JCheckBox(bundle.getString("Use_Offset"));

            // Panel to hold both the combobox and the checkbox
            JPanel panel = new JPanel();
            panel.add(new JLabel(bundle.getString("Select_a_direction_to_emboss_image")));
            panel.add(directionComboBox);
            panel.add(useOffsetCheckbox);

            int directionOption = JOptionPane.showOptionDialog(null, panel,
                    bundle.getString("Emboss_Filter_Options"), 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (directionOption == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (directionOption == JOptionPane.OK_OPTION) {
                String selectedDirection = (String) directionComboBox.getSelectedItem();

                if (selectedDirection == bundle.getString("Left")) {
                    direction = 1;
                } else if (selectedDirection == bundle.getString("Top_left")) {
                    direction = 2;
                } else if (selectedDirection == bundle.getString("Top")) {
                    direction = 3;
                } else if (selectedDirection == bundle.getString("Top_right")) {
                    direction = 4;
                } else if (selectedDirection == bundle.getString("Right")) {
                    direction = 5;
                } else if (selectedDirection == bundle.getString("Bottom_right")) {
                    direction = 6;
                } else if (selectedDirection == bundle.getString("Bottom")) {
                    direction = 7;
                } else if (selectedDirection == bundle.getString("Bottom_left")) {
                    direction = 8;
                }

                boolean useOffset = useOffsetCheckbox.isSelected(); // Get the state of the checkbox

                // Create and apply the filter
                target.getImage().apply(new EmbossFilter(direction, useOffset));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to outline an image with an sobel filter.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SobelFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sobel-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the sobel-filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * It prompts the user for an sobel direction, then applies the appropriate
         * {@link SobelFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the emboss direction - ask the user.
            int direction = 0; // Default to NONE SobelFilter

            // Pop-up dialog box to ask for the sobel direction value.
            String[] options = { bundle.getString("Horizontal"), bundle.getString("Vertical"),
                    bundle.getString("Combined") };
            JComboBox<String> directionComboBox = new JComboBox<>(options);

            // Create a checkbox for offset option
            JCheckBox offsetCheckBox = new JCheckBox(bundle.getString("Use_Offset"));

            // Create a panel to hold both the combobox and the checkbox
            JPanel panel = new JPanel();
            panel.add(new JLabel(bundle.getString("Select_a_direction_to_add_sobel_filter")));
            panel.add(directionComboBox);
            panel.add(offsetCheckBox);

            int result = JOptionPane.showConfirmDialog(null, panel, bundle.getString("Sobel_Filter_Options"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            // Check the return value from the dialog box.
            if (result == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (result == JOptionPane.OK_OPTION) {
                String selectedDirection = (String) directionComboBox.getSelectedItem();

                if (selectedDirection == bundle.getString("Horizontal")) {
                    direction = 1;
                } else if (selectedDirection == bundle.getString("Vertical")) {
                    direction = 2;
                } else if (selectedDirection == bundle.getString("Combined")) {
                    direction = 3;
                }

                boolean useOffset = offsetCheckBox.isSelected();

                // Create and apply the filter
                target.getImage().apply(new SobelFilter(direction, useOffset));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }
}
