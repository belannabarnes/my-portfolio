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

/**
 * Adds a random fun fact to the page.
 */
function addRandomFact() {
  const facts =
      ['I am an only child', 'My favorite color is blue', 'My name comes from a character on Star Trek: Voyager', 'I can play three instruments', 'I have two middle names'];

  // Pick a random fact.
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

function getData() {
  fetch('/data').then(response => response.json()).then((data) => {
    // data is an object, not a string, so we have to
    // reference its fields to create HTML content
    console.log(data);
    console.log(data[0]);
    console.log(data[1]);
    console.log(data[2]);

    const dataListElement = document.getElementById('data-container');
    dataListElement.innerHTML = '';
    data.forEach((item, index) => {
      dataListElement.appendChild(createListElement(`Comment ${index+1}: ${item}`));
    });
  });
}
/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

function drawTable() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Course Name');
    data.addColumn('string', 'When it Was Taken');
    data.addColumn('boolean', 'Required Class');
    data.addRows([
        ['Intro to Computer Science', 'Fall 2018', true],
        ['First-Year Engineering Seminar', 'Fall 2018',  false],
        ['Intro to Computer Engineering', 'Spring 2019', true],
        ['Seminar: Intro to Computer Science', 'Spring 2019',  false],
        ['Logic and Discrete Mathematics', 'Fall 2019', true],
        ['Data Structures and Algorithms', 'Fall 2019',  true],
        ['Web Development', 'Spring 2020', false],
        ['Programming Tools and Techniques', 'Spring 2020',  false],
        ['Seminar: Data Structures and Algorithms', 'Spring 2020',  false]
    ]);

    var table = new google.visualization.Table(document.getElementById('table-container'));

    table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
    }