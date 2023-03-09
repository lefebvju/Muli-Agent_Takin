import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class vueTaquin extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 120;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private JLabel title;
    private JLabel nbDeplacements;
    private JButton nouveau;
    private JButton lancer;
    private Grille grille;


    public vueTaquin(Grille _g) {
        final int[] nbAgent = new int[1];
        grille = _g;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[grille.getSizeX()][grille.getSizeY()];
        this.setTitle("MIF16- TP TAQUIN");



        //Mise en forme de la fenêtre
        JPanel contentPane = new JPanel(new GridLayout(grille.getSizeX(), grille.getSizeY()));
        for (int i = 0; i < grille.getSizeX(); i++) {
            for (int j = 0; j < grille.getSizeY(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 5);
                tabC[i][j] = new JLabel();
                tabC[i][j].setFont(new Font("SansSerif", Font.BOLD, PIXEL_PER_SQUARE/4));
                tabC[i][j].setBorder(border);
                tabC[i][j].setOpaque(true);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                contentPane.add(tabC[i][j]);

            }
        }
        getContentPane().add(contentPane, BorderLayout.CENTER);

        //JPanel contentPaneUp = new JPanel(new FlowLayout());
        JPanel contentPaneUp = new JPanel(new GridLayout(2, 1));
        contentPaneUp.setPreferredSize(new Dimension(500, 120));
        JPanel contentPaneDown = new JPanel(new GridLayout(3, 1));

        title = new JLabel();
        //instruction.setText("Join the numbers and get to the 2048 tile!");
        title.setText("Résolution taquin : multi-agents");
        title.setFont(new Font("SansSerif", Font.BOLD, PIXEL_PER_SQUARE/6));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPaneUp.add(title);

        JPanel contentPaneUpBottom = new JPanel(new FlowLayout());
        FlowLayout layoutUpBottom = (FlowLayout)contentPaneUpBottom.getLayout();
        layoutUpBottom.setVgap(0);

        JSpinner nbAgentInput =
                new JSpinner(new SpinnerNumberModel((int)((grille.getSizeX()*grille.getSizeY())*0.8),1,
                (grille.getSizeX()*grille.getSizeY())-1,1));
        contentPaneUpBottom.add(nbAgentInput);

        nouveau = new JButton("Nouveau");
        nouveau.setBackground(Color.decode("#F75E3E"));
        nouveau.setForeground(Color.white);
        nouveau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nbAgent[0] = (Integer) nbAgentInput.getValue();
                grille.init(nbAgent[0]);
            }
        }); 
        nouveau.setFocusable(false);
        contentPaneUpBottom.add(nouveau);

        contentPaneUp.add(contentPaneUpBottom);

        getContentPane().add(contentPaneUp, BorderLayout.NORTH);
      




        nbDeplacements = new JLabel();
        nbDeplacements.setText("Déplacements effectués : 0.");
        nbDeplacements.setHorizontalAlignment(SwingConstants.CENTER);
        contentPaneDown.add(nbDeplacements);

        JPanel contentPaneBottomDown = new JPanel(new FlowLayout());
        FlowLayout layoutBottomUp = (FlowLayout)contentPaneBottomDown.getLayout();
        layoutBottomUp.setVgap(0);

        lancer = new JButton("Lancer");
        lancer.setBackground(Color.decode("#FFE5AE"));
        //AIFullGame.setBackground(Color.lightGray);
        lancer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 grille.start(nbAgent[0]);
            }
        }); 
        lancer.setFocusable(false);
        contentPaneBottomDown.add(lancer);        

        contentPaneDown.add(contentPaneBottomDown);


       
        getContentPane().add(contentPaneDown, BorderLayout.SOUTH);
        contentPaneDown.setPreferredSize(new Dimension(500, 110));

        //System.out.println(contentPaneDown.getPreferredSize());
        //System.out.println(contentPaneUp.getPreferredSize());
        //System.out.println(contentPane.getPreferredSize());
        setSize(grille.getSizeX() * PIXEL_PER_SQUARE, grille.getSizeY() * PIXEL_PER_SQUARE + contentPaneDown.getPreferredSize().height + contentPaneUp.getPreferredSize().height);
        setLocationRelativeTo(null);

        rafraichir();
    }


    /**
     * Correspond à la fonctionnalité de Vue : affiche les données du modèle
     */
    private void rafraichir()  {

        SwingUtilities.invokeLater(new Runnable() { // demande au processus graphique de réaliser le traitement
            @Override
            public void run() {
                nbDeplacements.setText("Nombre de déplacements effectués : " + grille.getNbDep() + ".");

                for (int i = 0; i < grille.getSizeX(); i++) {
                    for (int j = 0; j < grille.getSizeY(); j++) {
                        int numCase = grille.getId(i, j);
                        int numCaseTarget = grille.getIdTarget(i, j);

                        if(grille.isFinished()){
                            tabC[i][j].setForeground(Color.WHITE);
                            tabC[i][j].setBackground(Color.decode("#2b2b2b"));
                            if (numCase == -1) {
                                tabC[i][j].setText(""); 
                            }else{
                                tabC[i][j].setText(numCase + "");
                            }
                        }
                        else{
                            if (numCase == -1) {
                                tabC[i][j].setText("");                            
                                tabC[i][j].setBackground(Color.decode("#cdc1b4"));
    
                            } else {    
                                tabC[i][j].setText(numCase + "");
                                tabC[i][j].setForeground(Color.WHITE);
                                if(numCaseTarget == numCase) {
                                    tabC[i][j].setBackground(Color.GRAY);
                                } else {
                                    tabC[i][j].setBackground(Color.decode("#f67c5f"));
                                }
                            }
                        }         

                    }
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
    }
}