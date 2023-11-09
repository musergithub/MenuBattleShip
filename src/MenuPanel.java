import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JComponent {
    Clip clip;
    Long currentFrame;
    String status;
    String currentPath;
    AudioInputStream audioInputStream;
    private Image image;
    private JButton musicButton;
    private JButton startButton;
    public MenuPanel(Image image)
    {
        this.image = image;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.weightx = 0.15;
        constraints.weighty = 0.01;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 20, 20, 0);

        Color backgroudColor = new Color(248, 143, 42);
        Color borderColor = new Color(255, 223, 26);
        musicButton = new JButton("");
        musicButton.setPreferredSize(new Dimension(70,70));
        ImageIcon icon = new ImageIcon(getClass().getResource("/Audio_On.png"));
        musicButton.setIcon(icon);
        musicButton.setBackground(backgroudColor);
        musicButton.setBorder(new LineBorder(borderColor, 3, true));
        add(musicButton, constraints);
        musicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status.equals("play"))
                {
                    currentFrame = clip.getMicrosecondPosition();
                    clip.stop();
                    status = "paused";
                    ImageIcon icon = new ImageIcon(getClass().getResource("/Audio_Off.png"));
                    musicButton.setIcon(icon);
                }
                else {
                    clip.close();
                    try {
                        reset();
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                    clip.setMicrosecondPosition(currentFrame);
                    play();
                    ImageIcon icon = new ImageIcon(getClass().getResource("/Audio_On.png"));
                    musicButton.setIcon(icon);
                }
            }
        });

        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(280, 90));
        startButton.setFont(new Font("Ariel", Font.BOLD, 36));
        startButton.setBackground(backgroudColor);
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(new LineBorder(borderColor, 3, true));
        constraints.gridy = 5;
        constraints.gridx = 1;
        constraints.weightx = 0.6;
        constraints.weighty = 0.25;
        constraints.insets = new Insets(140, 110, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        add(startButton, constraints);

        currentPath = getClass().getResource("/menu.wav").getPath();
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(currentPath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            play();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void reset() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(currentPath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play()
    {
        clip.start();
        status = "play";
    }
    public void restart() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clip.stop();
        clip.close();
        reset();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        play();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
