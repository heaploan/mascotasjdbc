package com.mycompany.mascotasjdbc.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputData {
    private BufferedReader br;
    public InputData(){
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String askStr(){
        String str;
        try{
            str = br.readLine().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
