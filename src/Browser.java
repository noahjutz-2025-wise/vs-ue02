import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import javax.swing.*;

public class Browser extends JFrame implements ActionListener {
  private int downloads;
  private JProgressBar[] balken;
  private JButton startButton;

  // Deklaration Ihrer Synchronisations-Hilfsklassen hier:
  private final CountDownLatch startLatch;
  private final CyclicBarrier stopLatch;

  public Browser(int downloads) {
    super("Mein Download-Browser");
    this.downloads = downloads;

    // Aufbau der GUI-Elemente:
    balken = new JProgressBar[downloads];
    JPanel zeilen = new JPanel(new GridLayout(downloads, 1));

    startLatch = new CountDownLatch(1);
    stopLatch =
        new CyclicBarrier(
            this.downloads,
            () -> {
              startButton.setText("DONE");
            });
    for (int i = 0; i < downloads; i++) {
      JPanel reihe = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 10));
      balken[i] = new JProgressBar(0, 100);
      balken[i].setPreferredSize(new Dimension(500, 20));
      reihe.add(balken[i]);
      zeilen.add(reihe);

      // neue Download-Threads erzeugen und starten
      // ggf. müssen Synchronisations-Objekte im Konstruktor übergeben werden!!
      // balken ist ebenfalls zu übergeben!
      // new Thread(new Download(balken[i], startLatch, stopLatch)).start();
      balken[i].setValue(1);
      final int _i = i;
      var t =
          new Thread(
              () -> {
                try {
                  startLatch.await();
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                for (int j = 0; j <= 100; j++) {
                  try {
                    Thread.sleep(new Random().nextInt(100));
                  } catch (InterruptedException _) {
                  }
                  balken[_i].setValue(j);
                }
                try {
                  stopLatch.await();
                } catch (InterruptedException | BrokenBarrierException _) {
                }
              });
      t.start();
    }

    startButton = new JButton("Downloads starten");
    startButton.addActionListener(this);

    this.add(zeilen, BorderLayout.CENTER);
    this.add(startButton, BorderLayout.SOUTH);

    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) throws InterruptedException {
    new Browser(5);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Blockierte Threads jetzt laufen lassen:
    startLatch.countDown();

    startButton.setEnabled(false);
    startButton.setSelected(false);
    startButton.setText("Downloads laufen...");

    // Auf Ende aller Download-Threads warten ... erst dann die Beschriftung ändern
    // Achtung, damit die Oberflaeche "reaktiv" bleibt dies in einem eigenen Runnable ausfuehren!

    // implemented in CyclicBarrier constructor, see above
  }
}
