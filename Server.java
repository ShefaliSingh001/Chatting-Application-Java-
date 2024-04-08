import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.io.*;//contains DataInputStream class
import java.net.*;//contains ServerSocket class and Socket class
import java.util.*;
import java.text.*;


public class Server implements ActionListener
{
    
    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f=new JFrame();
    static DataOutputStream dout;
    Server()
    {
        f.setLayout(null);
        

       
        // JLabel nm=new JLabel("Enter the name of user 1");
        // nm.setBounds()


        JPanel p1=new JPanel();//acts as header of the frame
        p1.setBackground(new Color(85,107,4));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));//image 
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);//to scale down image
        ImageIcon i3=new ImageIcon(i2);//only image ico obj can be passed to JFrame
        JLabel j1=new JLabel(i3);// image cant be added directly
        j1.setBounds(5,20,25,25);
        p1.add(j1); //p1.add shows that image is added on the top of the header

        j1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });

        ImageIcon pp1=new ImageIcon(ClassLoader.getSystemResource("icons/pp1.jpeg"));//image 
        Image pp1a=pp1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);//to scale down image
        ImageIcon pp1b=new ImageIcon(pp1a);//only image ico obj can be passed to JFrame
        JLabel j2=new JLabel(pp1b);// image cant be added directly
        j2.setBounds(40,10,50,50);
        p1.add(j2);

        JLabel name1=new JLabel("User 1");
        name1.setBounds(110,15,100,40);
        name1.setForeground(Color.WHITE);
        name1.setFont(new Font("",Font.BOLD,18));
        p1.add(name1);

        JLabel status1=new JLabel("Active Now");
        status1.setBounds(110,15,120,70);
        status1.setForeground(Color.WHITE);
        status1.setFont(new Font("",Font.BOLD,12));
        p1.add(status1);

        a1=new JPanel();
        a1.setBounds(10,75,430,550);
        f.add(a1);

        text=new JTextField();//the box where the text message haqs to be written
        text.setBounds(5,640,310,50);
        f.add(text);

        JButton send =new JButton("Send");
        send.setBounds(320,640,123,50);
        send.addActionListener(this);
        send.setFont(new Font("",Font.BOLD,14));
        f.add(send);

        f.setSize(450,700);  //dimensions of frame
        f.setLocation(250,50);//sets the location of the page on the screen
        f.setUndecorated(true);
        f.getContentPane().setBackground(new Color(245,245,220));




        f.setVisible(true); //makes frame visible, to be kept at the last
    
    }

    public void actionPerformed(ActionEvent ae)// used to make icons working 
    /*This is an abstract function in ActionListener class which 
      has to be overwitten in the class where it is being used that is here the Super class */
    {
        try{
        String out=text.getText();//gets the message entered by the user
        // System.out.println(out);

        JPanel p2=formatLabel(out);
       

        a1.setLayout(new BorderLayout());
        JPanel right =new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);//adds panel p2 hving the message to a panel right
        vertical.add(right);//right is added to a vertical box created
        vertical.add(Box.createVerticalStrut(10));

        a1.add(vertical,BorderLayout.PAGE_START);//vertical box is added to the main frame

        dout.writeUTF(out);
        text.setText("");//makes the texting block empty

        f.repaint();
        f.invalidate();
        f.validate();
        /*the last three commands are used to reload the frame */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output=new JLabel(out);
        output.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        output.setBackground(new Color(85,107,4));
        output.setForeground(Color.WHITE);//text color white
        output.setOpaque(true);//make the bg cplr visible
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal =Calendar.getInstance();//class for time
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");//time format
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));//time 
        panel.add(time);

        return panel;
    }

    public static void main(String args[])
    {
        new Server();
        try
        {
            ServerSocket skt=new ServerSocket(6001);
            while(true)
            {
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());

                //Protovol - Read UTf
                while(true)
                {   
                    String msg=din.readUTF();//contains the message sent by the client
                    JPanel panel=formatLabel(msg);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
