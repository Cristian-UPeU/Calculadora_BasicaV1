package Calculadora;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

//funciones de la calculadora
public class CalculadoraGUI extends javax.swing.JFrame {

    String Historial = "";
    String entrada = "";

    public static void main(String args[]) {
        new CalculadoraGUI();
    }

    public CalculadoraGUI() {
        initComponents();
        RefrescarPantalla();
        setTitle("Calculadora básica");
        new Thread() {
            @Override
            public void run() {
                BufferedImage icono;
                try {
                                        String dirIcono = "https://docs.google.com/drawings/d/e/2PACX-1vSU3b6BHsunmNOdPb73Fbx"
                            + "ooyRoglpb1riBZ6ALc7CBkAbK4ExZVklrrDmPU6eHviw3cMLBefZO0Fu0/pub?w=171&h=171";                   icono = ImageIO.read(new URL(dirIcono));
                    setIconImage(icono);
                } catch (Exception ex) {
                    System.err.println("El icono no se pudo cargar");
                }
            }
        }.start();
        setLocationRelativeTo(null);
        setVisible(true);
        AgregarAcciones();
    }

    void AgregarAcciones() {
        ((BotónTeclado) jLabel7).acción = new Acción() {
            @Override
            public void Ejecutar() {
                Historial = "";
                entrada = "";
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel8).acción = new Acción() {
            @Override
            public void Ejecutar() {
                entrada = "";
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel9).acción = new Acción() {
            @Override
            public void Ejecutar() {
                try {
                    entrada = entrada.substring(0, entrada.length() - 1);
                } catch (Exception e) {
                }
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel5).acción = new Acción() {
            @Override
            public void Ejecutar() {
                try {
                    int n = Integer.parseInt(entrada);
                    entrada = (n * n) + "";
                } catch (Exception e) {
                    try {
                        double n = Double.parseDouble(entrada);
                        entrada = (n * n) + "";
                    } catch (Exception e2) {
                    }
                }
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel6).acción = new Acción() {
            @Override
            public void Ejecutar() {
                try {
                    int n = Integer.parseInt(entrada);
                    entrada = (n * n * n) + "";
                } catch (Exception e) {
                    try {
                        double n = Double.parseDouble(entrada);
                        entrada = (n * n * n) + "";
                    } catch (Exception e2) {
                    }
                }
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel4).acción = new Acción() {
            @Override
            public void Ejecutar() {
                try {
                    int n = Integer.parseInt(entrada);
                    double sqrt = Math.sqrt(n);
                    if (sqrt == (int) sqrt) {
                        entrada = (int) sqrt + "";
                    } else {
                        entrada = String.format("%.4f", sqrt).replace(",", ".") + "";
                    }
                } catch (Exception e) {
                    try {
                        double n = Double.parseDouble(entrada);
                        double sqrt = Math.sqrt(n);
                        entrada = String.format("%.4f", sqrt).replace(",", ".") + "";
                    } catch (Exception e2) {
                    }
                }
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel23).acción = new Acción() {
            @Override
            public void Ejecutar() {
                try {
                    int n = Integer.parseInt(entrada);
                    entrada = -n + "";
                } catch (Exception e) {
                    try {
                        double n = Double.parseDouble(entrada);
                        if (n == (int) n) {
                            entrada = (int) (-n) + "";
                        }
                        entrada = -n + "";
                    } catch (Exception e2) {
                    }
                }
                RefrescarPantalla();
            }
        };
        ((BotónTeclado) jLabel25).acción = new Acción() {
            @Override
            public void Ejecutar() {
                if (!entrada.contains(".")) {
                    entrada += ".";
                }
                RefrescarPantalla();
            }
        };
        BotónTeclado[] operadores = {
            (BotónTeclado) jLabel10,
            (BotónTeclado) jLabel14,
            (BotónTeclado) jLabel18,
            (BotónTeclado) jLabel22
        };
        for (BotónTeclado operador : operadores) {
            operador.acción = new Acción() {
                @Override
                public void Ejecutar() {
                    if (!entrada.isEmpty()) {
                        Historial += entrada;
                        Historial += operador.getText();
                        entrada = "";
                    }
                    RefrescarPantalla();
                }
            };
        }
        BotónTeclado[] números = {
            (BotónTeclado) jLabel11,
            (BotónTeclado) jLabel12,
            (BotónTeclado) jLabel13,
            (BotónTeclado) jLabel15,
            (BotónTeclado) jLabel16,
            (BotónTeclado) jLabel17,
            (BotónTeclado) jLabel19,
            (BotónTeclado) jLabel20,
            (BotónTeclado) jLabel21,
            (BotónTeclado) jLabel24
        };
        for (BotónTeclado número : números) {
            número.acción = new Acción() {
                @Override
                public void Ejecutar() {
                    if (número.getText().equals("0")) {
                        if (entrada.isEmpty()) {
                            return;
                        }
                    }
                    entrada += número.getText();
                    RefrescarPantalla();
                }
            };
        }
        ((BotónTeclado) jLabel26).acción = new Acción() {
            @Override
            public void Ejecutar() {
                if (Historial.isEmpty()) {
                    return;
                }
                Historial += entrada;
                entrada = ObtenerSumas(Historial) + "";
                Historial = "";
                RefrescarPantalla();
            }

            String ObtenerSumas(String cadena) {
                String[] términos = cadena.split("\\+");
                double[] sumando = new double[términos.length];
                for (int i = 0; i < sumando.length; i++) {
                    try {
                        double d = Double.parseDouble(términos[i]);
                        sumando[i] = d;
                    } catch (Exception e) {
                        sumando[i] = ObterRestas(términos[i]);
                    }
                }
                double suma = 0;
                for (double d : sumando) {
                    suma += d;
                }
                if (suma == (int) suma) {
                    return (int) suma + "";
                }
                return suma + "";
            }

            double ObterRestas(String cadena) {
                boolean restar = cadena.startsWith("-");
                if (restar) {
                    cadena = cadena.substring(1);
                }
                String[] términos = cadena.split("-");
                double[] op = new double[términos.length];
                for (int i = 0; i < op.length; i++) {
                    try {
                        double d = Double.parseDouble(términos[i]);
                        op[i] = d;
                    } catch (Exception e) {
                        op[i] = ObterMultiplicaciones(términos[i]);
                    }
                }
                double resultado = 0;
                for (double d : op) {
                    if (restar) {
                        resultado -= d;
                    } else {
                        resultado += d;
                        restar = true;
                    }
                }
                return resultado;
            }

            double ObterMultiplicaciones(String cadena) {
                String[] términos = cadena.split("×");
                double[] fatores = new double[términos.length];
                for (int i = 0; i < fatores.length; i++) {
                    try {
                        double d = Double.parseDouble(términos[i]);
                        fatores[i] = d;
                    } catch (Exception e) {
                        fatores[i] = ObterDivisiones(términos[i]);
                    }
                }
                double producto = 1;
                for (double d : fatores) {
                    producto *= d;
                }
                return producto;
            }

            double ObterDivisiones(String cadena) {
                String[] términos = cadena.split("÷");
                double[] op = new double[términos.length];
                for (int i = 0; i < op.length; i++) {
                    try {
                        double d = Double.parseDouble(términos[i]);
                        op[i] = d;
                    } catch (Exception e) {
                        System.out.println("Error en: " + términos[i]);
                        e.printStackTrace();
                        throw new RuntimeException("Ocurrio un error en la interpretación");
                    }
                }
                double resultado = 0;
                boolean dividir = false;
                for (double d : op) {
                    if (dividir) {
                        resultado /= d;
                    } else {
                        resultado = d;
                        dividir = true;
                    }
                }
                return resultado;
            }
        };
    }

    public void RefrescarPantalla() {
        jLabel1.setText(Historial);
        if (entrada.equals("")) {
            jLabel2.setText("0");
        } else {
            jLabel2.setText(entrada);
        }
    }

    JLabel lblPantalla() {
        return new JLabel() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
            }
        };
    }

    JLabel lblTeclado() {
        return new BotónTeclado();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = lblTeclado();
        jLabel4 = lblTeclado();
        jLabel5 = lblTeclado();
        jLabel6 = lblTeclado();
        jLabel7 = lblTeclado();
        jLabel8 = lblTeclado();
        jLabel9 = lblTeclado();
        jLabel10 = lblTeclado();
        jLabel11 = lblTeclado();
        jLabel12 = lblTeclado();
        jLabel13 = lblTeclado();
        jLabel14 = lblTeclado();
        jLabel15 = lblTeclado();
        jLabel16 = lblTeclado();
        jLabel17 = lblTeclado();
        jLabel18 = lblTeclado();
        jLabel19 = lblTeclado();
        jLabel20 = lblTeclado();
        jLabel21 = lblTeclado();
        jLabel22 = lblTeclado();
        jLabel23 = lblTeclado();
        jLabel24 = lblTeclado();
        jLabel25 = lblTeclado();
        jLabel26 = lblTeclado();
        jLabel2 =  lblPantalla();
        jLabel1 =  lblPantalla();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(205, 214, 214));

        jPanel1.setBackground(new java.awt.Color(205, 214, 214));
        jPanel1.setLayout(new java.awt.GridLayout(6, 4, 3, 3));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("√");
        jPanel1.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("x²");
        jPanel1.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("x³");
        jPanel1.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CE");
        jPanel1.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("C");
        jPanel1.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("◄");
        jPanel1.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("÷");
        jPanel1.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("7");
        jPanel1.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("8");
        jPanel1.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("9");
        jPanel1.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("×");
        jPanel1.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("4");
        jPanel1.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("5");
        jPanel1.add(jLabel16);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("6");
        jPanel1.add(jLabel17);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("+");
        jPanel1.add(jLabel18);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("1");
        jPanel1.add(jLabel19);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("2");
        jPanel1.add(jLabel20);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("3");
        jPanel1.add(jLabel21);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("-");
        jPanel1.add(jLabel22);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("±");
        jPanel1.add(jLabel23);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("0");
        jPanel1.add(jLabel24);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(".");
        jPanel1.add(jLabel25);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("=");
        jPanel1.add(jLabel26);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 44)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("8893");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(122, 122, 122));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("4444+4444+5");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        int ascii = evt.getKeyCode();
        if (ascii >= KeyEvent.VK_NUMPAD0 && ascii <= KeyEvent.VK_NUMPAD9) {
            ascii = ascii - KeyEvent.VK_NUMPAD0 + KeyEvent.VK_0;
        }
        BotónTeclado btn = null;
        switch (ascii) {
            case KeyEvent.VK_0:
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_4:
            case KeyEvent.VK_5:
            case KeyEvent.VK_6:
            case KeyEvent.VK_7:
            case KeyEvent.VK_8:
            case KeyEvent.VK_9:
                String número = (char) ascii + "";
                if (número.equals("0")) {
                    if (entrada.isEmpty()) {
                        return;
                    }
                }
                entrada += número;
                RefrescarPantalla();
                return;
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
                btn = (BotónTeclado) jLabel18;
                break;
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
                btn = (BotónTeclado) jLabel22;
                break;
            case KeyEvent.VK_ASTERISK:
            case KeyEvent.VK_MULTIPLY:
                btn = (BotónTeclado) jLabel14;
                break;
            case KeyEvent.VK_SLASH:
            case KeyEvent.VK_DIVIDE:
                btn = (BotónTeclado) jLabel10;
                break;
            case KeyEvent.VK_EQUALS:
            case KeyEvent.VK_ENTER:
                btn = (BotónTeclado) jLabel26;
                break;
            case KeyEvent.VK_PERIOD:
            case KeyEvent.VK_DECIMAL:
                btn = (BotónTeclado) jLabel25;
                break;
            case KeyEvent.VK_BACK_SPACE:
                btn = (BotónTeclado) jLabel9;
                break;
        }
        if (btn != null) {
            btn.acción.Ejecutar();
        }
    }//GEN-LAST:event_formKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
