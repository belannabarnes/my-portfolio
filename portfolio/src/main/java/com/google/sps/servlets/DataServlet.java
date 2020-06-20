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
import java.util.List;
import com.google.sps.data.Task;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private ArrayList<String> comments = new ArrayList<String>();
    
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    for (Entity entity : results.asIterable()) {
      String text = (String) entity.getProperty("text");
      comments.add(text);
    }
    synchronized(comments){
      response.setContentType("application/json;");
      String json = convertToJsonUsingGson(comments);
      response.getWriter().println(json); 
      comments.clear();
    }
    
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //get input from the form
      String text = request.getParameter("comment-input");
    //   comments.add(text);

      Entity commentEntity = new Entity("Comment");
      commentEntity.setProperty("text", text);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);

      // Redirect back to the HTML page.
      response.sendRedirect("/index.html");
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
