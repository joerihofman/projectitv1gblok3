package yellowsparkle.Piechart;

/**
 * Created by MSI on 5-4-2016.
 */
import javax.swing.*;
import javax.swing.JPanel;


public class CreatePiechart extends JComponent {
    private Model model;
    private View countview;
    private View pieview;
    private Controller controller;

    public CreatePiechart(JPanel screen) {
        model=new Model();
        controller=new Controller(model);
        countview=new CountView(model);
        pieview=new PieView(model);
        //screen=new JFrame("Model View Controller/Dynamic Model with thread");
        //screen.setSize(450, 285);
        //screen.setResizable(false);
        screen.setLayout(null);
        screen.add(countview);
        screen.add(pieview);
        screen.add(controller);
        countview.setBounds(10, 10, 200, 200);
        pieview.setBounds(230, 10, 200, 200);
        controller.setBounds(0, 210, 450, 50);
    }
}