import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

/* Runnable demo of Java techniques for an electrical circuit design application
 * has NAND gates with connections etc and a drag'n'drop GUI
 * Main classes:
 * Component - abstract class for gates etc. Has inputs & outputs and a drawing method
 *   NANDGate - concrete Component - you can create others for NOR etc
 * Connector - abstract class for inputs and outputs
 *   Input, Output - concrete Connectors
 * Connection - joins two Connectors (joins one Input to one Output)
 *
 * These classes have the logic for GUI interaction (dragging, selecting Connectors etc)
 * but also have the potential for simulating the actual circuit behaviour.
 *
 */
public class PCB_Simulator {
    // NAND gate drag'n'drop demo with connections etc
    JFrame frame = new JFrame("drag demo");
    ArrayList<Component> components = new ArrayList<Component>();
    ArrayList<Connection> lines = new ArrayList<Connection>();
    private String savedTitle;
    JLabel prompt = new JLabel("Welcome. Add a component to start");
    DrawPanel drawPanel = new DrawPanel();
    boolean showInputs = true, showOutputs = true;
    boolean addingComponent = false, selectingOutput = false, selectingInput = false;
    Output selectedOutput = null;
    private JTextArea outPut;
    private JTextArea outPut2;
    private String name = "";
    private String value = "";

    PCB_Simulator() {

        frame.setMinimumSize(new Dimension(1280, 960));
        //frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(prompt, BorderLayout.PAGE_START);

        JMenuBar menubar = new JMenuBar();
        menubar.setBounds(5,10,988,45);

        ImageIcon icon = new ImageIcon("anyIcon.png");

        JMenu file = new JMenu("File");
        file.setBounds(5,10,10,10);
        file.setMnemonic(KeyEvent.VK_F);

        JMenu edit = new JMenu("Edit");
        edit.setBounds(5,10,10,10);
        edit.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit ", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");

        JMenuItem eSaveMenue = new JMenuItem("Save ", icon);
        eSaveMenue.setMnemonic(KeyEvent.VK_E);

        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        eSaveMenue.addActionListener((ActionEvent event) -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                savedTitle = selectedFile.getName();
                System.out.println(selectedFile.getName());
            }
        });

        file.add(eSaveMenue);
        file.add(eMenuItem);

        menubar.add(file);
        menubar.add(edit);

        JButton resistorButton = new JButton("Resistor");
        resistorButton.setBounds(20,20,150,45);


        resistorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField t1 = new JTextField();
                JTextField t2 = new JTextField();
                Object[] obj = {"Name: " ,t1, "Value: " , t2};
                int option = JOptionPane.showConfirmDialog(null,obj,"Resistor", JOptionPane.OK_CANCEL_OPTION);
                name = t1.getText();
                value = t2.getText();

                System.out.println(name);
                System.out.println(value);
                String temp = " ";

                if(name != temp  && value != temp){
                    outPut.append(name +" = "+ value+ " ohm \n");
                    //outPut.setText(name +" = "+ value+ " ohm");
                }
            }
        });

        drawPanel.setBorder(new TitledBorder (" "));
        drawPanel.setLayout(null);
        drawPanel.setBounds(210,80,1000,610);

        JPanel leftPanel = new JPanel();
        frame.add(leftPanel);
       //leftPanel.setLayout(null);
        leftPanel.setBorder(new TitledBorder (" "));
        leftPanel.setBounds(20,20,185,820);
        leftPanel.add(resistorButton);


        JPanel topPanel = new JPanel();
        topPanel.setBorder(new TitledBorder (" "));
        topPanel.setLayout(null);
        topPanel.setBounds(210,20,1000,60);

        topPanel.add(menubar);


        outPut = new JTextArea();
        outPut.setBounds(10,20,470,120);

        outPut2 = new JTextArea();
        outPut2.setBounds(10,20,470,120);


        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBorder(new TitledBorder ("Price-log"));
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(720,690, 490, 150);
        frame.add(bottomRightPanel);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new TitledBorder ("Output-log"));
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(210,690,490,150);
        frame.add(bottomPanel);
        bottomPanel.add(outPut);
        bottomRightPanel.add(outPut2);

        frame.add(topPanel);
        frame.add(drawPanel);



        JButton newComponentButton = new JButton("Add a Component");
        leftPanel.add(newComponentButton);
        newComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addNewComponent();
            }
        });
        JButton newConnectionButton = new JButton("Add a Connection");
        leftPanel.add(newConnectionButton);
        newConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addNewConnection();
            }
        });

        frame.setVisible(true);

    }
    void addNewComponent() {
        prompt.setText("Click to add component");
        addingComponent = true;
    }
    void addNewConnection() {
        prompt.setText("Click an output to connect...");
        selectingOutput = true;
        showInputs = false;
        drawPanel.repaint();
    }
    abstract class Component extends JLabel implements MouseListener, MouseMotionListener {
        // A component is a movable draggable object with inputs and outputs
        ArrayList<Input> inputs = new ArrayList<Input>();
        ArrayList<Output> outputs = new ArrayList<Output>();
        public Component(String text, int x, int y) {
            super(text);
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        public void addOutput(Output o) {
            outputs.add(o);
        }
        public void addInput(Input i) {
            inputs.add(i);
        }
        @Override
        public void paintComponent(Graphics g) {
            // can do custom drawing here, but just show JLabel text for now...
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (showInputs) {
                for (Input in : inputs) {
                    in.paintConnector(g2d);
                }
            }
            if (showOutputs) {
                for (Output out : outputs) {
                    if (out.isAvailable()) out.paintConnector(g2d);
                }
            }
        }
        int startDragX, startDragY;
        boolean inDrag = false;
        @Override
        public void mouseEntered(MouseEvent e) {
            // not interested
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // not interested
        }
        @Override
        public void mousePressed(MouseEvent e) {
            startDragX = e.getX();
            startDragY = e.getY();
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if (inDrag) {
                System.out.println("\"" + getText().trim() + "\" dragged to " + getX() + ", "
                        + getY());
                inDrag = false;
            }
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectingInput) {
                for (Input in : inputs) {
                    if (in.isAvailable() && in.contains(e.getPoint())) {
                        System.out.println("Click on input connector of \"" + getText().trim()
                                + "\"");
                        lines.add(new Connection(selectedOutput, in));
                        drawPanel.repaint();
                        selectingInput = false;
                        showOutputs = true;
                        prompt.setText(" ");
                    }
                }
            }
            if (selectingOutput) {
                for (Output out : outputs) {
                    if (out.isAvailable() && out.contains(e.getPoint())) {
                        System.out.println("Click on output connector of \"" + getText().trim()
                                + "\"");
                        selectedOutput = out;
                        selectingOutput = false;
                        showOutputs = false;
                        selectingInput = true;
                        showInputs = true;
                        prompt.setText("Click an input to connect...");
                        drawPanel.repaint();
                        return;
                    }
                }
            }
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            int newX = getX() + (e.getX() - startDragX);
            int newY = getY() + (e.getY() - startDragY);
            setLocation(newX, newY);
            inDrag = true;
            frame.repaint();

        }
        @Override
        public void mouseMoved(MouseEvent arg0) {
            // not interested
        }
    }
    class NANDgate extends Component {
        NANDgate(int x, int y) {
            super("    NAND " + (components.size() + 1), x, y);
            setVerticalAlignment(SwingConstants.CENTER);
            setBounds(x, y, 70, 30);
            setBorder(new LineBorder(Color.black, 1));
            new Input(this, 0, 6);
            new Input(this, 0, getHeight() - 6);
            new Output(this, getWidth(), getHeight() / 2);
        }
    }
    abstract class Connector { // subclasses: Input, Output
        Component component;
        ArrayList<Connection> connections = new ArrayList<Connection>();
        int maxConnections;
        int x, y; // x,y is the point where lines connect
        int w = 10, h = 10; // overall width & height of Connector
        Shape shape;
        Color color;
        Connector(Component c, int x, int y) {
            this.component = c;
            this.x = x;
            this.y = y;
        }
        boolean isAvailable() {
            // check that max fanout/fanin will not be exceeded
            return connections.size() < maxConnections;
        }
        void addConnection(Connection con) {
            if (isAvailable()) connections.add(con);
            // may want to show error somehow if not available?
        }
        public int getX() {
            return component.getX() + x;
        }
        public int getY() {
            return component.getY() + y;
        }
        public boolean contains(Point p) {
            // used to check for mouse clicks on this Connector
            return shape.contains(p);
        }
        public void paintConnector(Graphics2D g2d) {
            if (isAvailable()) g2d.setColor(color);
            else g2d.setColor(Color.lightGray);
            g2d.fill(shape);
        }
    }
    class Output extends Connector {
        // Triangle, left facing, at RHS of component (points away from component)
        Output(Component owner, int x, int y) {
            super(owner, x, y);
            owner.addOutput(this);
            maxConnections = 4; // fanout for output
            x = owner.getWidth(); // RHS of Component
            y = owner.getHeight() / 2;
            Polygon p = new Polygon();
            p.addPoint(x - w, y - h / 2);
            p.addPoint(x - w, y + h / 2);
            p.addPoint(x, y);
            shape = p;
            color = Color.red;
        }
    }
    class Input extends Connector {
        // Triangle, left facing, at LHS of component (points into the component)
        Input(Component owner, int x, int y) {
            super(owner, x, y);
            owner.addInput(this);
            maxConnections = 1; // only one connection to an input
            Polygon p = new Polygon();
            p.addPoint(x, y - h / 2);
            p.addPoint(x, y + h / 2);
            p.addPoint(x + w, y);
            shape = p;
            color = Color.blue;
        }
    }
    class Connection {
        // a Connection connects an Output to an Input.
        Output output;
        Input input;
        public Connection(Output output, Input input) {
            this.output = output;
            this.input = input;
            output.addConnection(this);
            input.addConnection(this);
        }
        public void paintConnection(Graphics2D g2d) {
            g2d.drawLine(output.getX(), output.getY(), input.getX(), input.getY());
        }
    }
    class DrawPanel extends JPanel {
        // contains Components, draws lines (connecting pairs of components)
        DrawPanel() {

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (addingComponent) {

                        Component c = new NANDgate(e.getX(), e.getY());
                        components.add(c);
                        drawPanel.add(c);
                        addingComponent = false;
                        prompt.setText("Use mouse to drag components");
                        drawPanel.repaint();
                    }
                }
            });
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            for (Connection line : lines) {
                line.paintConnection(g2d);
            }
        }
    }
    public static void main(String[] args) {
        new PCB_Simulator();
    }
}