package Calculadora;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

//funciones de botones
public class BotónTeclado extends JLabel {

    final String BotonesAzules[] = {"+", "-", "=", "×", "÷"};

    final byte ESTADO_CURSOR_FUERA = 0;
    final byte ESTADO_CURSOR_ENCIMA = 1;
    final byte ESTADO_CURSOR_PRESIONADO = 2;

    byte ESTADO_CURSOR = ESTADO_CURSOR_FUERA;

    public Acción acción;

    public BotónTeclado() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                ESTADO_CURSOR = ESTADO_CURSOR_PRESIONADO;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                ESTADO_CURSOR = ESTADO_CURSOR_ENCIMA;
                if (me.getX() > getWidth() || me.getY() > getHeight()) {
                    ESTADO_CURSOR = ESTADO_CURSOR_FUERA;
                }
                if (me.getX() < 0 || me.getY() < 0) {
                    ESTADO_CURSOR = ESTADO_CURSOR_FUERA;
                }
                if (ESTADO_CURSOR == ESTADO_CURSOR_ENCIMA) {
                    if (acción != null) {
                        acción.Ejecutar();
                    }
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                ESTADO_CURSOR = ESTADO_CURSOR_ENCIMA;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ESTADO_CURSOR = ESTADO_CURSOR_FUERA;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g = (Graphics2D) grphcs;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (ESTADO_CURSOR == ESTADO_CURSOR_FUERA) {
            try {
                Integer.parseInt(getText());
                g.setColor(Color.WHITE);
            } catch (Exception e) {
                g.setColor(new Color(230, 230, 230));
            }
        }
        boolean btnAzul = false;
        for (String TxtBtnAzul : BotonesAzules) {
            btnAzul |= getText().equals(TxtBtnAzul);
        }
        if (ESTADO_CURSOR == ESTADO_CURSOR_ENCIMA) {
            g.setColor(Color.LIGHT_GRAY);
            if (btnAzul) {
                g.setColor(new Color(0,163,255));
            }
        }
        if (ESTADO_CURSOR == ESTADO_CURSOR_PRESIONADO) {
            g.setColor(Color.GRAY);
            if (btnAzul) {
                g.setColor(new Color(128,209,255));
            }
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paint(g);
    }
}
