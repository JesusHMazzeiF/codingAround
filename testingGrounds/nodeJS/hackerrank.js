'use strict'
//Grading Students Challenge - Hackerrank
function gradingStudents(grades) {
  // Round up grade if next multiple of 5 is less than 3 points away. Do not round up grades lower than 38
  console.log(`Initial grades : ${grades}`)
  for(let i = 0 ; i <grades.length; i++) {
    if(grades[i] < 38)
      continue
    else if(grades[i]%5 >= 3)
      grades[i] = grades[i] + (5 - grades[i]%5) 
  }
  console.log(`Final grades : ${grades}`)
  return grades
}

gradingStudents([73,67,38,33])

