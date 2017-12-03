/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pro.polaco.aurora;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import net.sourceforge.javaflacencoder.FLACFileWriter;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 *
 * @author cassiano
 */
public class Main
{
    final static String API_LIC = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        Recognizer rec = new Recognizer(Recognizer.Languages.PORTUGUESE_BRASIL, API_LIC);
        SynthesiserV2 v2 = new SynthesiserV2(API_LIC);
        VoicePlayer v = new VoicePlayer();
        GPIO g = new GPIO();

        g.provision_pins();
        mic.setAudioFile(new File("/tmp/captura.flac"));
        v2.setLanguage("pt-br");
        v2.setPitch(1.0);
        v2.setSpeed(1.0);

        while (true)
        {
            mic.open();

            final int THRESHOLD = 70;
            int volume = mic.getAudioVolume();

            boolean isSpeaking = (volume > THRESHOLD);

            System.out.printf("Volume: %d\n", volume);

            if(isSpeaking && !v.running)
            {
                try
                {
                    System.out.println("Gravando...");
                    mic.captureAudioToFile(mic.getAudioFile());
                    do
                    {
                        Thread.sleep(1000);
                    } while (mic.getAudioVolume() > THRESHOLD);

                    // Send FLAC data to google servers and get response
                    GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);
                    displayResponse(response);

                    // Generate an audio stream and play it
                    InputStream in = new BufferedInputStream(v2.getMP3Data("Olá, eu sou a assistente Aurora!"));
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