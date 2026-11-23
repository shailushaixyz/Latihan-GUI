import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiDemo {
    private final JFrame frame;

    private final JLabel judul;
    private final JTextField tfNama, tfNim, tfKetik;
    private final JButton btnSimpan, btnReset, btnTentang;
    private final JButton btnTambah, btnKurang, btnNol;
    private final JLabel lblCounter, lblKey;
    private final JTextArea log;

    private int counter = 0;

    public GuiDemo() {
        frame = new JFrame("Latihan GUI Java - PBO");
        judul = new JLabel("Latihan GUI & Event Listener", JLabel.CENTER);
        tfNama = new JTextField(18);
        tfNim = new JTextField(18);
        tfKetik = new JTextField(18);
        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnTentang = new JButton("Tentang");
        btnTambah = new JButton("+");
        btnKurang = new JButton("-");
        btnNol = new JButton("0");
        lblCounter = new JLabel("Counter: 0", JLabel.CENTER);
        lblKey = new JLabel("Tombol terakhir: -");
        log = new JTextArea(6, 40);
    }

    public void launchFrame() {

        frame.getContentPane().setLayout(new BorderLayout(5, 5));

        judul.setFont(new Font("Arial", Font.BOLD, 20));
        judul.setForeground(Color.BLUE);
        judul.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(judul, BorderLayout.NORTH);

        JTabbedPane tab = new JTabbedPane();
        tab.add(buatPanelForm(), "Form (GridBagLayout)");
        tab.add(buatPanelCounter(), "Counter (FlowLayout + GridLayout)");
        frame.getContentPane().add(tab, BorderLayout.CENTER);

        log.setEditable(false);
        JScrollPane scroll = new JScrollPane(log);
        scroll.setBorder(BorderFactory.createTitledBorder("Log Event"));
        frame.getContentPane().add(scroll, BorderLayout.SOUTH);

        btnSimpan.addActionListener(new SimpanHandler());
        tfNim.addActionListener(new SimpanHandler());
        btnReset.addActionListener(new ResetHandler());
        btnTentang.addActionListener(new TentangHandler());
        CounterHandler ch = new CounterHandler();
        btnTambah.addActionListener(ch);
        btnKurang.addActionListener(ch);
        btnNol.addActionListener(ch);
        judul.addMouseListener(new JudulMouseHandler());
        tfKetik.addKeyListener(new KeyHandler());
        frame.addWindowListener(new CloseHandler());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(520, 440);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel buatPanelForm() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        p.add(new JLabel("Nama"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        p.add(tfNama, c);

        c.gridx = 0; c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        p.add(new JLabel("NIM"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        p.add(tfNim, c);

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        tombol.add(btnSimpan);
        tombol.add(btnReset);
        tombol.add(btnTentang);
        c.gridx = 0; c.gridy = 2;
        c.gridwidth = 2;
        p.add(tombol, c);
        return p;
    }

    private JPanel buatPanelCounter() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        lblCounter.setFont(new Font("Arial", Font.BOLD, 24));
        p.add(lblCounter, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1, 3, 10, 10));
        grid.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        grid.add(btnTambah);
        grid.add(btnKurang);
        grid.add(btnNol);
        p.add(grid, BorderLayout.CENTER);

        JPanel bawah = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bawah.add(new JLabel("Ketik di sini:"));
        bawah.add(tfKetik);
        bawah.add(lblKey);
        p.add(bawah, BorderLayout.SOUTH);
        return p;
    }

    private void tulisLog(String s) {
        log.append(s + "\n");
    }

    private class SimpanHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tulisLog("Simpan -> Nama: " + tfNama.getText() + ", NIM: " + tfNim.getText());
        }
    }

    private class ResetHandler implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            tfNama.setText("");
            tfNim.setText("");
            tulisLog("Form direset");
        }
    }

    private class TentangHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Latihan GUI Java dengan Swing",
                    "Tentang", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class CounterHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "+" -> {
                    counter++;
                    break;
                }
                case "-" -> {
                    counter--;
                    break;
                }
                default ->
                    counter = 0;
            }
            lblCounter.setText("Counter: " + counter);
            tulisLog("Tombol " + cmd + " ditekan, counter = " + counter);
        }
    }

    private class JudulMouseHandler extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            judul.setForeground(Color.RED);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            judul.setForeground(Color.BLUE);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            tulisLog("Judul diklik di X = " + e.getX() + ", Y = " + e.getY());
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            lblKey.setText("Tombol terakhir: " + e.getKeyChar());
        }
    }

    private class CloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int hasil = JOptionPane.showConfirmDialog(frame, "Yakin ingin keluar?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (hasil == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiDemo().launchFrame());
    }
}
