package com.example.catolica2017.atividade;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView superficieDesenho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        superficieDesenho = new GLSurfaceView(this);
        setContentView(superficieDesenho);

        Renderizador renderizador = new Renderizador();
        superficieDesenho.setRenderer(renderizador);
    }
}

class Renderizador implements GLSurfaceView.Renderer {
    private float larguraTela;
    private float alturaTela;
    private float lado = 90.0f;
    private float angulo = 30.0f;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int largura, int altura) {
        gl10.glViewport(0, 0, largura, altura);
        this.larguraTela = largura;
        this.alturaTela = altura;

        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();
        gl10.glOrthof(0, largura, 0, altura, 1, -1);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();

        gl10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        float[] coordenadas = {
                -lado, -lado,
                -lado, lado,
                lado, -lado,
                lado, lado
        };

        FloatBuffer floatBuffer = Renderizador.criarVetorCoordenadas(coordenadas);

        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glVertexPointer(2, GL10.GL_FLOAT, 0, floatBuffer);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        //carrega a matriz identida na model view
        gl10.glLoadIdentity();

        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl10.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        gl10.glTranslatef(larguraTela / 2, alturaTela / 2, 0);


        gl10.glRotatef(10.0f,0,0,1);


        //desenha o sol na tela
        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl10.glPushMatrix();


        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);

    }

    public static FloatBuffer criarVetorCoordenadas(float[] coordenadas) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coordenadas.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.clear();

        floatBuffer.put(coordenadas);
        floatBuffer.flip();

        return floatBuffer;
    }

}
