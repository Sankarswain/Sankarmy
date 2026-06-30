
package sankarmy;


public class Sankarmy {

    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // यहाँ AuthForm को शुरू करने का कोड लिखें
            new sankarmy.AuthForm().setVisible(true); 
        }
    });
    }
    
}
