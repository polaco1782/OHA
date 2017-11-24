/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pro.polaco.cacilda;

import java.io.File;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import net.sourceforge.javaflacencoder.FLACFileWriter;


/**
 *
 * @author cassiano
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        mic.setAudioFile(new File("/tmp/captura.flac"));

        while (true)
        {
            mic.open();

            final int THRESHOLD = 70;
            int volume = mic.getAudioVolume();

            boolean isSpeaking = (volume > THRESHOLD);

            System.out.printf("Volume: %d\n", volume);

            if(isSpeaking)
            {
                try
                {
                    System.out.println("Gravando...");
                    mic.captureAudioToFile(mic.getAudioFile());
                    do
                    {
                        Thread.sleep(1000);
                    } while (mic.getAudioVolume() > THRESHOLD);

                    //Recognizer rec = new Recognizer(Recognizer.Languages.PORTUGUESE_BRASIL, "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
                    //GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);
                    //displayResponse(response);

                    SynthesiserV2 v2 = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
                    v2.setLanguage("pt-br");
                    v2.setPitch(1.0);
                    v2.setSpeed(1.0);

                    InputStream in = new BufferedInputStream(v2.getMP3Data("Olá, eu sou a assistente Cacilda!"));

                    VoicePlayer v = new VoicePlayer();
                    v.play_stream(in);
                } 
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("Error Occured");
                }
                finally
                {
                    mic.close();
                }
            }
        }
    }

    private static void displayResponse(GoogleResponse gr)
    {
        if (gr.getResponse() == null)
        {
            System.out.println((String) null);
            return;
        }
        System.out.println("Google Response: " + gr.getResponse());
        System.out.println("Google is " + Double.parseDouble(gr.getConfidence()) * 100 + "% confident in"
                + " the reply");
        System.out.println("Other Possible responses are: ");

        for (String s : gr.getOtherPossibleResponses())
        {
            System.out.println("\t" + s);
        }
    }
}