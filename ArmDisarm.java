package armdisarm;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ArmDisarm extends JFrame implements ActionListener {
    
    private static final int BUFFER_SIZE = 4096;
    String audioOne = "C:\\Users\\ADMIN\\Desktop\\sound files\\arm.wav";
    String audioTwo = "C:\\Users\\ADMIN\\Desktop\\sound files\\disarm.wav";
    JButton b1, b2;

    ArmDisarm() {
        setLayout(new FlowLayout());  
        b1 = new JButton("Arm");
        b2 = new JButton("Disarm");
        this.add(b1);
        this.add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        setSize(500, 500);
        setResizable(true);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equalsIgnoreCase("Arm")) {
            play(audioOne);
        } else if (str.equalsIgnoreCase("Disarm")) {
        
            play(audioTwo);
        }
    }

    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

             SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            audioLine.open(format);

            audioLine.start();

            System.out.println("Playback started.");

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();
            audioLine.stop();
            System.out.println("Playback completed.");

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArmDisarm au = new ArmDisarm();
        au.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
