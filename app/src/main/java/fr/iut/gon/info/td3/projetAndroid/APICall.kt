package fr.iut.gon.info.td3.projetAndroid

import org.json.JSONArray
import java.lang.StringBuilder
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class APICall {
    companion object{
        val MAIN_URL = "https://dev-restandroid.users.info.unicaen.fr/api/"

        fun fetchDives() : List<DiveDataclass>{
            val url = URL(MAIN_URL + "plongees")
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()

            if(connection.responseCode != 200){
                throw RequestFailureException("Received code " + connection.responseCode.toString())
            }

            val jsonObjects = JSONArray(fetchAllDataFromConnection(connection))

            connection.disconnect()

            val dives : MutableList<DiveDataclass> = mutableListOf()

            for(i in 0..<jsonObjects.length()){
                val jsonObject = jsonObjects.getJSONObject(i)

                dives.add(DiveDataclass(
                    jsonObject.getString("date"),
                    jsonObject.getString("moment"),
                    jsonObject.getString("niveau"),
                    0,
                    jsonObject.getString("lieu"),
                    jsonObject.getInt("max_plongeurs"),
                    0,
                    null,
                    false,
                ))
            }

            return dives
        }

        fun registerToDive(id: Int){
            throw RuntimeException()
        }

        fun createDive(dive: DiveDataclass){
            throw RuntimeException()
        }

        private fun fetchAllDataFromConnection(connection : HttpsURLConnection): String{
            val stream = connection.inputStream.bufferedReader()
            val content = StringBuilder()

            var line = stream.readLine()
            while(line != null){
                content.append(line)
                line = stream.readLine()
            }
            return content.toString()
        }
    }
}