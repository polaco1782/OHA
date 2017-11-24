/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pro.polaco.cacilda;

import java.io.InputStream;
import java.util.Map;
import main.java.goxr3plus.javastreamplayer.stream.StreamPlayer;
import main.java.goxr3plus.javastreamplayer.stream.StreamPlayerEvent;
import main.java.goxr3plus.javastreamplayer.stream.StreamPlayerException;
import main.java.goxr3plus.javastreamplayer.stream.StreamPlayerListener;

/**
 *
 * @author cassiano
 */
public class VoicePlayer extends StreamPlayer implements StreamPlayerListener
{
    public VoicePlayer()
    {
        addStreamPlayerListener(this);
    }

    @Override
    public void opened(Object o, Map<String, Object> map)
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map<String, Object> map)
    {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void statusUpdated(StreamPlayerEvent spe)
    {
        System.out.println("Status do player: " + spe.getPlayerStatus());

      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void play_stream(InputStream s)
    {
        try
        {
            open(s);
            play();
        }
        catch(StreamPlayerException e)
        {
            e.printStackTrace();
        }
    }
}
