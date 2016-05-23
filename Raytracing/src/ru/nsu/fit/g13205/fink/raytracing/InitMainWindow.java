package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private final int HEADER_SIZE = 54;
    private Data data = new Data();
    private JLabel statusBar = new JLabel("Ready");
    private InitView initView;
    private OptionsDialog optionsDialog;
    private JScrollPane scrollPane;
    private boolean renderMode = false;

    public InitMainWindow() {
        super();
        setTitle("Raytracing");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open", KeyEvent.VK_X, "Open.png", "onOpen", statusBar);
            addMenuItem("File/Load", "Load render settings", KeyEvent.VK_X, "Load.gif", "onLoad", statusBar);
            addMenuItem("File/Save", "Save render settings", KeyEvent.VK_X, "Save.png", "onSave", statusBar);
            addMenuItem("File/Save image", "Save image", KeyEvent.VK_X, "SaveImage.png", "onSaveImage", statusBar);
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit", statusBar);
            addSubMenu("Edit", KeyEvent.VK_F);
            addMenuItem("Edit/Init", "Init", KeyEvent.VK_X, "Init.png", "onInit", statusBar);
            addMenuItem("Edit/Options", "Options", KeyEvent.VK_X, "Options.png", "onOptions", statusBar);
            addMenuItem("Edit/Select", "Select view", KeyEvent.VK_X, "Select.png", "onSelect", statusBar);
            addMenuItem("Edit/Render", "Render", KeyEvent.VK_X, "Render.png", "onRender", statusBar);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/Open", "Open", statusBar);
            addToolBarButton("File/Load", "Load render settings", statusBar);
            addToolBarButton("File/Save", "Save render settings", statusBar);
            addToolBarButton("File/Save image", "Save image", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Init", "Init", statusBar);
            addToolBarButton("Edit/Options", "Options", statusBar);
            addToolBarButton("Edit/Select", "Select view", statusBar);
            addToolBarButton("Edit/Render", "Render", statusBar);
            addToolBarSeparator();
            addToolBarButton("Help/About", "Information about author", statusBar);

            add(statusBar, BorderLayout.SOUTH);
            try {
                data = Parser.parse(new File(FileUtils.getDataDirectory().getAbsolutePath() + "\\StandfordBunny.scene"));
            } catch (Exception ex) {
            }
            getMenuElement("Edit/Select").getComponent().setEnabled(renderMode);
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(!renderMode);
            initView = new InitView(data, statusBar);
            scrollPane = new JScrollPane(initView);
            add(scrollPane);
            
            addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("sdff");
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("sd");
                }
            }

        });
            
        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onOpen() {
        File file = getOpenFileName("scene", "Scene file");
        try {
            data = Parser.parse(file);
            renderMode = false;
            getMenuElement("Edit/Select").getComponent().setEnabled(renderMode);
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(!renderMode);
            getMenuElement("Edit/Render").getComponent().setEnabled(!renderMode);
            ((JButton) toolBar.getComponentAtIndex(8)).setSelected(renderMode);
            initView.setRenderMode(renderMode);
            initView.updateData(data);
        } catch (FileNotFoundException | NullPointerException e) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onLoad() {
        File file = getOpenFileName("render", "Render file");
        try {
            RenderData renderData = ParserRender.parse(file);
            renderMode = false;
            getMenuElement("Edit/Select").getComponent().setEnabled(renderMode);
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(!renderMode);
            getMenuElement("Edit/Render").getComponent().setEnabled(!renderMode);
            ((JButton) toolBar.getComponentAtIndex(8)).setSelected(renderMode);
            initView.setRenderMode(renderMode);
            data.setRenderData(renderData);
            initView.draw();
        } catch (FileNotFoundException | NullPointerException e) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onSave() {
        File file = getSaveFileName("render", "Render file");
        try (PrintWriter out = new PrintWriter(file)) {
            out.println((int) (data.getBr() * 255) + " " + (int) (data.getBg() * 255) + " " + (int) (data.getBb() * 255));
            out.println(data.getGamma());
            out.println(data.getDepth());
            out.println(data.getQuality());
            out.println(new Coordinate3D(MatrixOperation.multiply(MatrixOperation.transporter(data.getRotateMatrix()), data.getEyeVector().getMatrix())));
            out.println(new Coordinate3D(MatrixOperation.multiply(MatrixOperation.transporter(data.getRotateMatrix()), data.getRefVector().getMatrix())));
            out.println(new Coordinate3D(MatrixOperation.multiply(MatrixOperation.transporter(data.getRotateMatrix()), data.getUpVector().getMatrix())));
            out.println(data.getZn() + " " + data.getZf());
            out.println(data.getSw() + " " + data.getSh());
        } catch (Exception e) {
        }
    }

    public void onSaveImage() {
        File file = getSaveFileName("bmp", "Bitmap");
        try (OutputStream out = new FileOutputStream(file)) {
            BufferedImage image = initView.getImage();
            int width = image.getWidth();
            int height = image.getHeight();
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

    public void onOptions() {
        if (optionsDialog == null || !optionsDialog.isVisible()) {
            optionsDialog = new OptionsDialog(this, data, initView);
        }
        optionsDialog.setVisible(true);
    }

    public void onInit() {
        data.clear();
        initView.draw();
    }

    public void onSelect() {
        if (renderMode) {
            renderMode = false;
            getMenuElement("Edit/Select").getComponent().setEnabled(renderMode);
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(!renderMode);
            getMenuElement("Edit/Render").getComponent().setEnabled(!renderMode);
            ((JButton) toolBar.getComponentAtIndex(8)).setSelected(renderMode);
            initView.setRenderMode(false);
            initView.draw();
        }
    }

    public void onRender() {
        if (!renderMode) {
            renderMode = true;
            getMenuElement("Edit/Select").getComponent().setEnabled(renderMode);
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(!renderMode);
            getMenuElement("Edit/Render").getComponent().setEnabled(!renderMode);
            ((JButton) toolBar.getComponentAtIndex(8)).setSelected(renderMode);
            initView.setRenderMode(renderMode);
            initView.render();
        }
    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Fink Artem, NSU, FIT, group 13205", "About Author", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }

}
