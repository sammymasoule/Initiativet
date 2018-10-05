package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jespe on 10-Jan-18.
 */

public class api_call {
    //init
    ArrayList<String> apiLovNummer;
    ArrayList<String> apiLovTitel;
    ArrayList<String> apiLovTitelKort;
    ArrayList<String> apiLovResume;
    ArrayList<String> apiLovHeader;
    Gson gson;

    //get api list of categories.
    public ArrayList<String> getApiLovNummer(){ return apiLovNummer; }
    public ArrayList<String> getApiLovTitel(){ return apiLovTitel; }
    public ArrayList<String> getApiLovTitelKort(){ return apiLovTitelKort; }
    public ArrayList<String> getApiLovResume(){ return apiLovResume; }
    public ArrayList<String> getApiLovHeader(){ return apiLovHeader; }

    //Constructor
    public api_call() {
        JsonDeserializer<Value> valueJsonDeserializer = new JsonDeserializer<Value>() {
            @Override
            public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                    Value value = new Value(
                            jsonObject.get("nummer").getAsString(),
                            jsonObject.get("titel").getAsString(),
                            jsonObject.get("titelkort").getAsString(),
                            jsonObject.get("resume").getAsString()
                    );
                    return value;
            }
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(Value.class, valueJsonDeserializer)
                .create();
    }

    public void fetchData(final Runnable runnable){
        apiLovNummer = new ArrayList<String>();
        apiLovTitel = new ArrayList<String>();
        apiLovTitelKort = new ArrayList<String>();
        apiLovResume = new ArrayList<String>();
        apiLovHeader = new ArrayList<String>();

        getUrlData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonReader reader = new JsonReader(response.body().charStream());
                Valg lov = gson.fromJson(reader, Valg.class);
                for (Value value: lov.getValue()) {
                    apiLovHeader.add(value.getNummer() +" - "+ value.getTitelkort());
                    apiLovNummer.add(value.getNummer());
                    apiLovTitel.add(value.getTitel());
                    apiLovTitelKort.add(value.getTitelkort());
                    apiLovResume.add(value.getResume());
                }
                runnable.run();
            }
        });
    }

    public class Valg{
        private String odatacount;
        private String odatanextLink;
        private Value[] value;
        private String odatametadata;

        public String getOdatacount (){return odatacount;}
        public void setOdatacount (String odatacount){this.odatacount = odatacount;}
        public String getOdatanextLink (){return odatanextLink;}
        public void setOdatanextLink (String odatanextLink){this.odatanextLink = odatanextLink;}
        public Value[] getValue (){return value;}
        public void setValue (Value[] value){this.value = value;}
        public String getOdatametadata (){return odatametadata;}
        public void setOdatametadata (String odatametadata){this.odatametadata = odatametadata;}
    }

    public class Value {
        private String afgørelsesresultatkode;
        private String nummernumerisk;
        private String statsbudgetsag;
        private String lovnummer;
        private String id;
        private String lovnummerdato;
        private String typeid;
        private String fremsatundersagid;
        private String afgørelsesdato;
        private String offentlighedskode;
        private int kategoriid;
        private String rådsmødedato;
        private String begrundelse;
        private String retsinformationsurl;
        private String nummer;
        private String statusid;
        private String nummerprefix;
        private String deltundersagid;
        private String afstemningskonklusion;
        private String periodeid;
        private String resume;
        private String opdateringsdato;
        private String titel;
        private String paragraf;
        private String baggrundsmateriale;
        private String afgørelse;
        private String titelkort;
        private String nummerpostfix;
        private String paragrafnummer;

        public Value(String nummer, String titel, String titelkort, String resume) {
            this.nummer = nummer;
            this.titel = titel;
            this.titelkort = titelkort;
            this.resume = resume;
        }

        public String getAfgørelsesresultatkode (){return afgørelsesresultatkode;}
        public void setAfgørelsesresultatkode (String afgørelsesresultatkode){this.afgørelsesresultatkode = afgørelsesresultatkode;}
        public String getNummernumerisk (){return nummernumerisk;}
        public void setNummernumerisk (String nummernumerisk){this.nummernumerisk = nummernumerisk;}
        public String getStatsbudgetsag (){return statsbudgetsag;}
        public void setStatsbudgetsag (String statsbudgetsag){this.statsbudgetsag = statsbudgetsag;}
        public String getLovnummer (){return lovnummer;}
        public void setLovnummer (String lovnummer){this.lovnummer = lovnummer;}
        public String getId (){return id;}
        public void setId (String id){this.id = id;}
        public String getLovnummerdato (){return lovnummerdato;}
        public void setLovnummerdato (String lovnummerdato){this.lovnummerdato = lovnummerdato;}
        public String getTypeid (){return typeid;}
        public void setTypeid (String typeid){this.typeid = typeid;}
        public String getFremsatundersagid (){return fremsatundersagid;}
        public void setFremsatundersagid (String fremsatundersagid){this.fremsatundersagid = fremsatundersagid;}
        public String getAfgørelsesdato (){return afgørelsesdato;}
        public void setAfgørelsesdato (String afgørelsesdato){this.afgørelsesdato = afgørelsesdato;}
        public String getOffentlighedskode (){return offentlighedskode;}
        public void setOffentlighedskode (String offentlighedskode){this.offentlighedskode = offentlighedskode;}
        public int getKategoriid (){return kategoriid;}
        public void setKategoriid (int kategoriid){this.kategoriid = kategoriid;}
        public String getRådsmødedato (){return rådsmødedato;}
        public void setRådsmødedato (String rådsmødedato){this.rådsmødedato = rådsmødedato;}
        public String getBegrundelse (){return begrundelse;}
        public void setBegrundelse (String begrundelse){this.begrundelse = begrundelse;}
        public String getRetsinformationsurl (){return retsinformationsurl;}
        public void setRetsinformationsurl (String retsinformationsurl){this.retsinformationsurl = retsinformationsurl;}
        public String getNummer (){return nummer;}
        public void setNummer (String nummer){this.nummer = nummer;}
        public String getStatusid (){return statusid;}
        public void setStatusid (String statusid){this.statusid = statusid;}
        public String getNummerprefix (){return nummerprefix;}
        public void setNummerprefix (String nummerprefix){this.nummerprefix = nummerprefix;}
        public String getDeltundersagid (){return deltundersagid;}
        public void setDeltundersagid (String deltundersagid){this.deltundersagid = deltundersagid;}
        public String getAfstemningskonklusion (){return afstemningskonklusion;}
        public void setAfstemningskonklusion (String afstemningskonklusion){this.afstemningskonklusion = afstemningskonklusion;}
        public String getPeriodeid (){return periodeid;}
        public void setPeriodeid (String periodeid){this.periodeid = periodeid;}
        public String getResume (){return resume;}
        public void setResume (String resume){this.resume = resume;}
        public String getOpdateringsdato (){return opdateringsdato;}
        public void setOpdateringsdato (String opdateringsdato){this.opdateringsdato = opdateringsdato;}
        public String getTitel (){return titel;}
        public void setTitel (String titel){this.titel = titel;}
        public String getParagraf (){return paragraf;}
        public void setParagraf (String paragraf){this.paragraf = paragraf;}
        public String getBaggrundsmateriale (){return baggrundsmateriale;}
        public void setBaggrundsmateriale (String baggrundsmateriale){this.baggrundsmateriale = baggrundsmateriale;}
        public String getAfgørelse (){return afgørelse;}
        public void setAfgørelse (String afgørelse){this.afgørelse = afgørelse;}
        public String getTitelkort (){return titelkort;}
        public void setTitelkort (String titelkort){this.titelkort = titelkort;}
        public String getNummerpostfix (){return nummerpostfix;}
        public void setNummerpostfix (String nummerpostfix){this.nummerpostfix = nummerpostfix;}
        public String getParagrafnummer (){return paragrafnummer;}
        public void setParagrafnummer (String paragrafnummer){this.paragrafnummer = paragrafnummer;}
    }

    public void getUrlData(Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2025")
                .build();

        Request request2 = new Request.Builder()
                .url("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2025&$skip=20")
                .build();

        Request request3 = new Request.Builder()
                .url("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2025&$skip=40")
                .build();

        Request request4 = new Request.Builder()
                .url("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2025&$skip=60")
                .build();

        client.newCall(request).enqueue(callback);
        client.newCall(request2).enqueue(callback);
        client.newCall(request3).enqueue(callback);
        client.newCall(request4).enqueue(callback);
    }
}