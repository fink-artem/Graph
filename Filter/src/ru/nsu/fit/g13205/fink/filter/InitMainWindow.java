package ru.nsu.fit.g13205.fink.filter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private JScrollPane scrollPane;
    private InitView initView = new InitView();
    private JLabel statusBar = new JLabel("Ready");
    private boolean selectMode = false;
    private GammaDialog gammaDialog;

    public InitMainWindow() {
        super();
        setTitle("Filter");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/New", "Create a new document", KeyEvent.VK_X, "New.png", "onNew", statusBar);
            addMenuItem("File/Open", "Open an existing document", KeyEvent.VK_X, "Open.png", "onOpen", statusBar);
            addMenuItem("File/Save", "Save the active document", KeyEvent.VK_X, "Save.png", "onSave", statusBar);
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit", statusBar);
            addSubMenu("Edit", KeyEvent.VK_F);
            addMenuItem("Edit/Select", "Select a part of image", KeyEvent.VK_X, "Select.png", "onSelect", statusBar);
            addMenuSeparator("Edit");
            addMenuItem("Edit/Black And White", "Black and white", KeyEvent.VK_X, "BlackAndWhite.png", "onBlackAndWhite", statusBar);
            addMenuItem("Edit/Negative", "Negative transformation", KeyEvent.VK_X, "Negative.png", "onNegative", statusBar);
            addMenuItem("Edit/Sharpness", "Image sharpness", KeyEvent.VK_X, "Sharpness.png", "onSharpness", statusBar);
            addMenuItem("Edit/Stamping", "Image stamping", KeyEvent.VK_X, "Stamping.png", "onStamping", statusBar);
            addMenuSeparator("Edit");
            addMenuItem("Edit/Gamma", "Gamma correction", KeyEvent.VK_X, "Gamma.png", "onGamma", statusBar);

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/New", "Create a new document", statusBar);
            addToolBarButton("File/Open", "Open an existing document", statusBar);
            addToolBarButton("File/Save", "Save the active document", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Select", "Select a part of image", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Black And White", "Black and white", statusBar);
            addToolBarButton("Edit/Negative", "Negative transformation", statusBar);
            addToolBarButton("Edit/Sharpness", "Image sharpness", statusBar);
            addToolBarButton("Edit/Stamping", "Image stamping", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Gamma", "Gamma correction", statusBar);
            addToolBarSeparator();
            addToolBarButton("Help/About", "Information about author", statusBar);

            add(statusBar, BorderLayout.SOUTH);
            scrollPane = new JScrollPane(initView);
            add(scrollPane);
            //addWindowListener(new CloseWindowListener(this));
        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNew() {
        initView.clear();
    }

    public void onOpen() {
        File file = getOpenFileName("bmp", "");
        try (InputStream in = new FileInputStream(file)) {
            byte headerByte[] = new byte[54];
            int headerInt[] = new int[54];
            in.read(headerByte, 0, 54);
            for (int i = 0; i < 54; i++) {
                headerInt[i] = headerByte[i];
                if (headerInt[i] < 0) {
                    headerInt[i] += 256;
                }
            }
            int width = 256 * headerInt[19] + headerInt[18];
            int height = 256 * headerInt[23] + headerInt[22];
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            byte b[] = new byte[3];
            int blue;
            int green;
            int red;
            for (int i = height - 1; i >= 0; i--) {
                for (int j = 0; j < width; j++) {
                    in.read(b, 0, 3);
                    blue = b[0];
                    if (blue < 0) {
                        blue += 256;
                    }
                    green = b[1];
                    if (green < 0) {
                        green += 256;
                    }
                    red = b[2];
                    if (red < 0) {
                        red += 256;
                    }
                    bufferedImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
                }
            }
            initView.setImageInZone(bufferedImage, ZoneName.ZONE_A);
        } catch (Exception ex) {
        }
    }

    public void onSave() {
        File file = getSaveFileName("bmp", "");
        try (PrintWriter out = new PrintWriter(file)) {

        } catch (FileNotFoundException | NullPointerException e) {
        }
    }

    public void onSelect() {
        selectMode = !selectMode;
        ((JButton) toolBar.getComponentAtIndex(4)).setSelected(selectMode);
        initView.setAllocationMode(selectMode);
    }
    
    public void onBlackAndWhite(){
        initView.blackAndWhiteTransformation();
    }
    
    public void onNegative(){
        initView.negativeTransformation();
    }
    
    public void onSharpness(){
        initView.sharpness();
    }
    
    public void onStamping(){
        initView.stamping();
    }

    public void onGamma() {
        if (gammaDialog == null) {
            gammaDialog = new GammaDialog(initView);
        }
        gammaDialog.setVisible(true);
    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Fink Artem, NSU, FIT, group 13205", "About Author", JOptionPane.INFORMATION_MESSAGE);
    }

    public void rescroll() {
        scrollPane.updateUI();
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }

}
