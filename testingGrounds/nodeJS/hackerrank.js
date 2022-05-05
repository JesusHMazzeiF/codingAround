'use strict';
function plusMinus(arr) {
  // Write your code here
  let positiveRatio = 0;
  let zeroRatio = 0;
  let negativeRatio = 0;
  for (const item of arr) {
    if (item > 0) {
      positiveRatio += 1 / arr.length;
    } else if (item == 0) {
      zeroRatio += 1 / arr.length;
    } else {
      negativeRatio += 1 / arr.length;
    }
  }
  console.log(positiveRatio.toFixed(6));
  console.log(Math.abs(negativeRatio).toFixed(6));
  console.log(zeroRatio.toFixed(6));
}

const array = [1, 1, 0, -1, -1];

function staircase(n) {
  // Write your code here
  for (let i = 1; i <= n; i++) {
    console.log(' '.repeat(n - i).concat('#'.repeat(i)));
  }
}

// staircase(6);

function timeConversion(s) {
  // Write your code here
  const hour = parseInt(s.substr(0, 2));
  const amOrPM = s.substr(8);
  if (amOrPM === 'AM') {
    if (hour === 12) return '00'.concat(s.substring(2, 8));
    else if (hour < 10) return '0'.concat(hour).concat(s.substring(2, 8));
    else return String(hour).concat(s.substring(2, 8));
  } else {
    if (hour === 12) return String(hour).concat(s.substring(2, 8));
    else return String(hour + 12).concat(s.substring(2, 8));
  }
}

let s = '01:05:45AM';

// timeConversion(s);

//Grading Students Challenge - Hackerrank
function gradingStudents(grades) {
  // Round up grade if next multiple of 5 is less than 3 points away. Do not round up grades lower than 38
  console.log(`Initial grades : ${grades}`);
  for (let i = 0; i < grades.length; i++) {
    if (grades[i] < 38) continue;
    else if (grades[i] % 5 >= 3) grades[i] = grades[i] + (5 - (grades[i] % 5));
  }
  console.log(`Final grades : ${grades}`);
  return grades;
}

gradingStudents([73, 67, 38, 33]);
