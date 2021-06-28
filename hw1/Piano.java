import edu.princeton.cs.introcs.StdAudio;
import es.datastructur.synthesizer.GuitarString;
public class Piano {
    private final GuitarString[] frequencyArray = new GuitarString[37];
    private final String sequence = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public void fillArray() {
        for (int i = 0; i < 37; i = i + 1) {
            frequencyArray[i] = new GuitarString(440 * Math.pow(2, (i - 24) / 12));
        }
    }

    public static void main(String[] args) {
        Piano p = new Piano();
        p.fillArray();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int k = p.sequence.indexOf(key);
                if (p.sequence.indexOf(key) != -1) {
                    GuitarString gs = p.frequencyArray[p.sequence.indexOf(key)];
                    gs.pluck();
                }
            }

            double sample = 0.0;
            for (int i = 0; i < 37; i = i + 1) {
                sample = sample + p.frequencyArray[i].sample();
            }
            StdAudio.play(sample);

            for (int i = 0; i < 37; i = i + 1) {
               p.frequencyArray[i].tic();
            }

        }

    }
}
