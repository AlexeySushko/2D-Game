import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Alexey Sushko 29/09/2018
 */
public class Audio {
    private String track;//adress track
    private Clip clip;
    private FloatControl volumeControll;// volume controller
    private double wt;//volume

    public Audio(String track, double wt) {
        this.track = track;
        this.wt = wt;
    }

    public void sound(){
        File file = new File(this.track);

        AudioInputStream tr = null;
        try {
            tr = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(tr);
            volumeControll = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void setVolume(){
        if(wt<0){
            wt = 0;
        }
        if(wt > 1){
            wt = 1;
        }
        float min = volumeControll.getMinimum();
        float max = volumeControll.getMaximum();
        volumeControll.setValue((max - min) * (float)wt + min);
    }

    public void stopSound(){
        if(clip != null){
            clip.stop();
        }
    }

}
