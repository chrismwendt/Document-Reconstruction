import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;

@SuppressWarnings("serial")
public class BackgroundSubtractionStep extends Step implements MouseMotionListener, KeyListener {
    BufferedImage originalImage;
    BufferedImage processedImage;
    HashSet<Color> foregroundColors = new HashSet<Color>();
    HashSet<Color> backgroundColors = new HashSet<Color>();

    public BackgroundSubtractionStep(Listener listener) {
        super(listener);

        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void begin(Object input) {
        originalImage = (BufferedImage) input;
        processedImage = Utility.addAlphaChannel(originalImage);
        setPreferredSize(new Dimension(originalImage.getWidth(), originalImage.getHeight()));
        // TODO listener.update(this, output);
    }

    @Override
    public void paint(Graphics g) {
        if (processedImage != null) {
            g.drawImage(processedImage, 0, 0, processedImage.getWidth(), processedImage.getHeight(), null);
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        switch (arg0.getButton()) {
        case MouseEvent.BUTTON1:
            foregroundColors.add(new Color(originalImage.getRGB(arg0.getX(), arg0.getY())));
            break;
        case MouseEvent.BUTTON2:
            backgroundColors.add(new Color(originalImage.getRGB(arg0.getX(), arg0.getY())));
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_F:
            foregroundColors.clear();
            break;
        case KeyEvent.VK_B:
            backgroundColors.clear();
            break;
        case KeyEvent.VK_ENTER:
            subtractBackground();
            break;
        }
    }

    private void subtractBackground() {
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
