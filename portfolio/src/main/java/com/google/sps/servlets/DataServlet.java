// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private ArrayList<String> comments = new ArrayList<String>();
    
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    synchronized(comments){
       comments.add("Hello World!");
       comments.add("Hola Mundo!");
       comments.add("Hallo Welt!");
       response.setContentType("application/json;");
       String json = convertToJsonUsingGson(comments);
       response.getWriter().println(json); 
       comments.clear();
    }
    
    
  }

  /**
   * Converts a ServerStats instance into a JSON string using manual String concatentation.
   */
  private String convertToJson(ArrayList<String> comments) {
    String json = "{";
    json += "\"Comment 1\": ";
    json += "\"" + comments.get(0) + "\"";
    json += ", ";
    json += "\"Comment 2\": ";
    json += "\"" + comments.get(1) + "\"";
    json += ", ";
    json += "\"Comment 3\": ";
    json += "\"" + comments.get(2) + "\"";
    json += "}";
    return json;
  }

  /**
   * Converts a ServerStats instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList<String> comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }
}
