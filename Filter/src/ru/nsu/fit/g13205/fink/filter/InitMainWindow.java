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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private final int HEADER_SIZE = 54;
    private JScrollPane scrollPane;
    private InitView initView = new InitView();
    private JLabel statusBar = new JLabel("Ready");
    private boolean selectMode = false;
    private GammaDialog gammaDialog;
    private SliderAndTextDialog sobelDialog;
    private SliderAndTextDialog robertDialog;
    private SliderAndTextDialog turnDialog;
    private DialogRGB floydDitheringDialog;
    private DialogRGB orderedDitheringDialog;

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
            addMenuItem("Edit/Copy", "Copy C to B", KeyEvent.VK_X, "Copy.png", "onCopy", statusBar);
            addMenuSeparator("Edit");
            addMenuItem("Edit/Black And White", "Black and white", KeyEvent.VK_X, "BlackAndWhite.png", "onBlackAndWhite", statusBar);
            addMenuItem("Edit/Negative", "Negative transformation", KeyEvent.VK_X, "Negative.png", "onNegative", statusBar);
            addMenuItem("Edit/Floyd dithering", "Floyd-Stenberg dithering", KeyEvent.VK_X, "FloydDithering.png", "onFloydDithering", statusBar);
            addMenuItem("Edit/Ordered dithering", "Ordered dithering", KeyEvent.VK_X, "OrderedDithering.png", "onOrderedDithering", statusBar);
            addMenuItem("Edit/Zoom", "Zoom 2x", KeyEvent.VK_X, "Zoom.png", "onZoom", statusBar);
            addMenuItem("Edit/Sobel", "Sobel operator", KeyEvent.VK_X, "Sobel.png", "onSobel", statusBar);
            addMenuItem("Edit/Robert", "Robert operator", KeyEvent.VK_X, "Robert.png", "onRobert", statusBar);
            addMenuItem("Edit/Smoothing", "Image smoothing", KeyEvent.VK_X, "Smoothing.png", "onSmoothing", statusBar);
            addMenuItem("Edit/Sharpness", "Image sharpness", KeyEvent.VK_X, "Sharpness.png", "onSharpness", statusBar);
            addMenuItem("Edit/Stamping", "Image stamping", KeyEvent.VK_X, "Stamping.png", "onStamping", statusBar);
            addMenuItem("Edit/Watercolor", "Watercolor correction", KeyEvent.VK_X, "Watercolor.png", "onWatercolor", statusBar);
            addMenuSeparator("Edit");
            addMenuItem("Edit/Turn", "Image turn", KeyEvent.VK_X, "Turn.png", "onTurn", statusBar);
            addMenuItem("Edit/Gamma", "Gamma correction", KeyEvent.VK_X, "Gamma.png", "onGamma", statusBar);

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/New", "Create a new document", statusBar);
            addToolBarButton("File/Open", "Open an existing document", statusBar);
            addToolBarButton("File/Save", "Save the active document", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Select", "Select a part of image", statusBar);
            addToolBarButton("Edit/Copy", "Copy C to B", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Black And White", "Black and white", statusBar);
            addToolBarButton("Edit/Negative", "Negative transformation", statusBar);
            addToolBarButton("Edit/Floyd dithering", "Floyd-Stenberg dithering", statusBar);
            addToolBarButton("Edit/Ordered dithering", "Ordered dithering", statusBar);
            addToolBarButton("Edit/Zoom", "Zoom 2x", statusBar);
            addToolBarButton("Edit/Sobel", "Sobel operator", statusBar);
            addToolBarButton("Edit/Robert", "Robert operator", statusBar);
            addToolBarButton("Edit/Smoothing", "Image smoothing", statusBar);
            addToolBarButton("Edit/Sharpness", "Image sharpness", statusBar);
            addToolBarButton("Edit/Stamping", "Image stamping", statusBar);
            addToolBarButton("Edit/Watercolor", "Watercolor correction", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Turn", "Image turn", statusBar);
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
        File file = getOpenFileName("bmp", "Bitmap");
        try (InputStream in = new FileInputStream(file)) {
            byte headerByte[] = new byte[HEADER_SIZE];
            int headerInt[] = new int[HEADER_SIZE];
            in.read(headerByte, 0, HEADER_SIZE);
            for (int i = 0; i < HEADER_SIZE; i++) {
                headerInt[i] = headerByte[i];
                if (headerInt[i] < 0) {
                    headerInt[i] += 256;
                }
            }
            if (headerInt[0] != 'B' || headerInt[1] != 'M') {
                throw new Exception();
            }
            int width = ((headerInt[21] * 256 + headerInt[20]) * 256 + headerInt[19]) * 256 + headerInt[18];
            int height = ((headerInt[25] * 256 + headerInt[24]) * 256 + headerInt[23]) * 256 + headerInt[22];
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            byte b[] = new byte[3];
            int blue;
            int green;
            int red;
            int pass = (4 - 3 * width % 4) % 4;
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
                in.skip(pass);
            }
            initView.setImageInZone(bufferedImage, ZoneName.ZONE_A);
        } catch (FileNotFoundException | NullPointerException e) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onSave() {
        BufferedImage image = initView.getImageZone(ZoneName.ZONE_C);
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Image not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        File file = getSaveFileName("bmp", "Bitmap");
        try (OutputStream out = new FileOutputStream(file)) {
            byte header[] = new byte[HEADER_SIZE];
            header[0] = 'B';
            header[1] = 'M';
            int pass = (4 - 3 * width % 4) % 4;
            int newWidth = width * 3 + pass;
            int fileSize = HEADER_SIZE + height * newWidth;
            for (int i = 2; i < 6; i++) {
                header[i] = (byte) (fileSize % 256);
                fileSize /= 256;
            }
            int offset = 54;
            for (int i = 10; i < 14; i++) {
                header[i] = (byte) (offset % 256);
                offset /= 256;
            }
            int headerSize = 40;
            for (int i = 14; i < 18; i++) {
                header[i] = (byte) (headerSize % 256);
                headerSize /= 256;
            }
            int imageWidth = width;
            for (int i = 18; i < 22; i++) {
                header[i] = (byte) (imageWidth % 256);
                imageWidth /= 256;
            }
            int imageHeight = height;
            for (int i = 22; i < 26; i++) {
                header[i] = (byte) (imageHeight % 256);
                imageHeight /= 256;
            }
            int number = 1;
            for (int i = 26; i < 28; i++) {
                header[i] = (byte) (number % 256);
                number /= 256;
            }
            number = 24;
            for (int i = 28; i < 30; i++) {
                header[i] = (byte) (number % 256);
                number /= 256;
            }
            out.write(header, 0, HEADER_SIZE);
            byte buffer[] = new byte[newWidth];
            Color color;
            for (int i = height - 1; i >= 0; i--) {
                for (int j = 0; j < width; j++) {
                    color = new Color(image.getRGB(j, i));
                    buffer[3 * j] = (byte) color.getBlue();
                    buffer[3 * j + 1] = (byte) color.getGreen();
                    buffer[3 * j + 2] = (byte) color.getRed();
                }
                out.write(buffer, 0, newWidth);
            }
        } catch (Exception e) {
        }
    }

    public void onSelect() {
        selectMode = !selectMode;
        ((JButton) toolBar.getComponentAtIndex(4)).setSelected(selectMode);
        initView.setAllocationMode(selectMode);
    }

    public void onCopy() {
        initView.setImageInZone(initView.getImageZone(ZoneName.ZONE_C), ZoneName.ZONE_B);
    }

    public void onBlackAndWhite() {
        initView.setImageInZone(Filter.blackAndWhiteTransformation(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onNegative() {
        initView.setImageInZone(Filter.negativeTransformation(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onFloydDithering() {
        if (floydDitheringDialog == null) {
            floydDitheringDialog = new DialogRGB();
        }
        floydDitheringDialog.setVisible(true);
        if (floydDitheringDialog.getStatus() == DialogRGB.SUCCESS) {
            Color color = floydDitheringDialog.getValue();
            initView.setImageInZone(Filter.floydDithering(initView.getImageZone(ZoneName.ZONE_B), color.getRed(), color.getGreen(), color.getBlue()), ZoneName.ZONE_C);
        }
    }
    
    public void onOrderedDithering() {
        if (orderedDitheringDialog == null) {
            orderedDitheringDialog = new DialogRGB();
        }
        orderedDitheringDialog.setVisible(true);
        if (orderedDitheringDialog.getStatus() == DialogRGB.SUCCESS) {
            Color color = orderedDitheringDialog.getValue();
            initView.setImageInZone(Filter.orderedDithering(initView.getImageZone(ZoneName.ZONE_B), color.getRed(), color.getGreen(), color.getBlue()), ZoneName.ZONE_C);
        }
    }

    public void onZoom() {
        initView.setImageInZone(Filter.zoom(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onSobel() {
        if (sobelDialog == null) {
            sobelDialog = new SliderAndTextDialog(10, 250, 80, 40, "Sobel operator", "Level");
        }
        sobelDialog.setVisible(true);
        if (sobelDialog.getStatus() == SliderAndTextDialog.SUCCESS) {
            initView.setImageInZone(Filter.sobel(Filter.blackAndWhiteTransformation(initView.getImageZone(ZoneName.ZONE_B)), sobelDialog.getValue()), ZoneName.ZONE_C);
        }
    }

    public void onRobert() {
        if (robertDialog == null) {
            robertDialog = new SliderAndTextDialog(10, 250, 80, 40, "Robert dialog", "Level");
        }
        robertDialog.setVisible(true);
        if (robertDialog.getStatus() == SliderAndTextDialog.SUCCESS) {
            initView.setImageInZone(Filter.robert(Filter.blackAndWhiteTransformation(initView.getImageZone(ZoneName.ZONE_B)), robertDialog.getValue()), ZoneName.ZONE_C);
        }
    }

    public void onSmoothing() {
        initView.setImageInZone(Filter.smoothing(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onSharpness() {
        initView.setImageInZone(Filter.sharpness(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onStamping() {
        initView.setImageInZone(Filter.stamping(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onWatercolor() {
        initView.setImageInZone(Filter.watercolorCorrection(initView.getImageZone(ZoneName.ZONE_B)), ZoneName.ZONE_C);
    }

    public void onTurn() {
        if (turnDialog == null) {
            turnDialog = new SliderAndTextDialog(-180, 180, 0, 60, "Rotation angle", "Angle");
        }
        turnDialog.setVisible(true);
        if (turnDialog.getStatus() == SliderAndTextDialog.SUCCESS) {
            initView.setImageInZone(Filter.turn(initView.getImageZone(ZoneName.ZONE_B), turnDialog.getValue()), ZoneName.ZONE_C);
        }
    }

    public void onGamma() {
        if (gammaDialog == null) {
            gammaDialog = new GammaDialog();
        }
        gammaDialog.setVisible(true);
        if (gammaDialog.getStatus() == GammaDialog.SUCCESS) {
            initView.setImageInZone(Filter.gammaCorrection(initView.getImageZone(ZoneName.ZONE_B), gammaDialog.getValue()), ZoneName.ZONE_C);
        }
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
        try {
            InitMainWindow mainFrame = new InitMainWindow();
            mainFrame.setVisible(true);
        } catch (Exception e) {
        }
    }

}
