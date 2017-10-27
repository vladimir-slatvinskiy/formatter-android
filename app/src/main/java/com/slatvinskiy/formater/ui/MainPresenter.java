package com.slatvinskiy.formater.ui;

import android.util.Log;

import com.slatvinskiy.formater.base.BasePresenter;
import com.slatvinskiy.formater.base.MvpView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Vlad on 27.10.2017.
 */

public class MainPresenter extends BasePresenter<MvpView> {

    private final static String[] charset = {".", "!", "?"};

    public MainPresenter() {

    }

    @Override
    public void attachView(MvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    public Observable<String> processText(String text){
        String out = "";

        out = prepareString(text);
        for(int i=0; i<charset.length; i++){
            out = processSymbols(out, charset[i]);
        }
        out = processComma(out);
        out = processApostrof(out);

        Timber.d("processSentences: %s", out);
        return Observable.just(out);
    }


    private String prepareString(String text){
        String out = "";

        if(text != null && text.length()>0){
            String[] words = text.trim().toLowerCase().split("\\s+");
            if(words != null && words.length>0){
                for(int i=0; i<words.length; i++){
                    String part = words[i];

                    if(i< words.length-1){
                        out += part + " ";
                    }else{
                        out += part;
                    }
                }
            }
        }

        Timber.d("prepareString: %s", out);
        return out;
    }

    private String processSymbols(String text, String symbol){
        String out = "";

        if(symbol != null && symbol.length()>0){
            String[] dotsParts = text.split("\\" + symbol);
            if(dotsParts != null && dotsParts.length>0){
                for(int i=0; i<dotsParts.length; i++){
                    String part = dotsParts[i].trim();

                    if(part.length()>0){
                        part = part.substring(0, 1).toUpperCase() + part.substring(1);
                    }

                    if(i<dotsParts.length-1){
                        out += part + symbol;
                    }else{
                        if(text.endsWith(symbol)){
                            out += part + symbol;
                        }else{
                            out += part;
                        }
                    }
                }
            }
        }

        int index=-1;
        while(true){
            index = out.indexOf(symbol, index + 1);
            if(index != -1){
                if(index != out.length()-1 && !startFromCharset(out.substring(index + 1))){
                    out = out.substring(0, index+1) + " " + out.substring(index + 1);
                }
            }else{
                break;
            }
        }

        Timber.d("processSymbol: %s", out);
        return out;
    }


    private String processComma(String text){
        String out = "";

        if(text != null && text.length()>0){
            String[] commasParts = text.split("\\,");
            if(commasParts != null && commasParts.length>0){
                for(int i=0; i<commasParts.length; i++){
                    String part = commasParts[i].trim();

                    if(i< commasParts.length-1){
                        out += part + ", ";
                    }else{
                        if(text.endsWith(",")){
                            out += part + ",";
                        }else{
                            out += part;
                        }
                    }
                }
            }
        }

        Timber.d("processComma: %s", out);
        return out;
    }

    private String processApostrof(String text){
        String out = "";

        if(text != null && text.length()>0){
            String[] commasParts = text.split("\\’");
            if(commasParts != null && commasParts.length>0){
                for(int i=0; i<commasParts.length; i++){
                    String part = commasParts[i].trim();

                    if(i< commasParts.length-1){
                        out += part + "’";
                    }else{
                        if(text.endsWith("’")){
                            out += part + "’";
                        }else{
                            out += part;
                        }
                    }
                }
            }
        }

        Timber.d("processApostrof: %s", out);
        return out;
    }


    private boolean startFromCharset(String text){
        boolean res = false;
        for(int j=0; j<charset.length; j++) {
            String symbol = charset[j];
            if(text.startsWith(symbol)){
                res = true;
                break;
            }
        }
        return res;
    }

}

