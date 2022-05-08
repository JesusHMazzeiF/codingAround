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


function plusMinusImproved(arr) {
  console.log((arr.filter(item => item > 0).length / arr.length).toFixed(6))
  console.log((arr.filter(item => item < 0).length / arr.length).toFixed(6))
  console.log((arr.filter(item => item == 0).length / arr.length).toFixed(6))
}

const array = [1, 1, 0, -1, -1];
// plusMinus(array)

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

// gradingStudents([73, 67, 38, 33]);

function miniMaxSum(arr) {
  const totalSum = arr.reduce((previousValue, currentValue) => previousValue + currentValue)
  const sortedArra = arr.sort()
  console.log(`${totalSum - sortedArra[arr.length - 1]} ${totalSum - sortedArra[0]}`)

}

// miniMaxSum([1,3,5,7,9])

function findMedian(arr) {
  // Write your co
  const sortedArr = arr.sort()
  return sortedArr[(sortedArr.length - 1) / 2]
}

// console.log(findMedian([5,3,1,2,4]))

function lonleyInteger(a) {
  if (a.length === 1)
    return a[0];
  const aSorted = a.sort()
  for (let i = 1; i < aSorted.length; i++) {
    if (aSorted[i] !== aSorted[i + 1] && aSorted[i] !== aSorted[i - 1])
      return aSorted[i];
  }
}


function diagonalDifference(arr) {
  let ltrDiagonal = 0, rtlDiagonal = 0;
  for (let i = 0; i < arr.length; i++) {
    ltrDiagonal += arr[i][i]
    rtlDiagonal += arr[i][arr.length - 1 - i]
  }
  return Math.abs(ltrDiagonal - rtlDiagonal)
}

// console.log(diagonalDifference([[1,2,3], [4,5,6], [9,8,9]]))

function countingSort(arr) {
  const fArr = new Array(100).fill(0)
  for (const item of arr) {
    fArr[item]++
  }
  return fArr
}


// console.log(countingSort([1,2,3,1,2,3,1,1,33]))

function flippingTheMatrix(matrix) {
  let totalSum = 0
  const n = matrix.length - 1
  for (let i = 0; i < matrix.length / 2; i++) {
    for (let j = 0; j < matrix[i].length / 2; j++) {
      totalSum += Math.max(
        matrix[i][j],
        matrix[i][n - j],
        matrix[n - i][j],
        matrix[n - i][n - j],
      )
    }
  }
  return totalSum
}
const exampleArray = [[112, 42, 83, 119], [56, 125, 56, 49], [15, 78, 101, 43], [62, 98, 114, 108]]

// console.log(flippingTheMatrix(exampleArray))

const twoDimensionalArray = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9],
];

function transpose(matrix) {
  return matrix[0].map((col, i) => matrix.map(row => row[i]));
}

// console.log(transpose(twoDimensionalArray))}

// console.log(Math.max(2,3,6,13))

function towerBreakers(n, m) {
  return (m === 1 || n % 2 == 0) ? 2 : 1
}

function caesarsCipher(s, k) {
  let newString = ''
  for (let i = 0; i < s.length; i++) {
    let asciiChar = s[i].charCodeAt(0);
    let newAciichar = asciiChar + k
    if (asciiChar >= 65 && asciiChar <= 90) {
      if (newAciichar > 90) {
        newString = newString.concat(String.fromCodePoint((newAciichar % 90) + 64))
      }
      else {
        newString = newString.concat(String.fromCodePoint(newAciichar))
      }
    }
    else if (asciiChar >= 97 && asciiChar <= 122) {
      if (newAciichar > 122) {
        newString = newString.concat(String.fromCodePoint((newAciichar % 122) + 96))
      }
      else {
        newString = newString.concat(String.fromCodePoint(newAciichar))
      }
    }
    else {
      newString = newString.concat(s[i])
    }
  }
  return newString
}



// console.log(caesarsCipher("www.abc.xy", 87))

// function palindromeIndex(s) {
//   // Write your code here
//   let index = -1
//   let removedIndex = false
//   for(let i = 0; i < s.length; i++){
//     let leftI = 0 + i
//     let rightI = s.length - 1 - i
//     if(s[leftI] !== s[rightI]){
//       if(s[leftI+1] === s[rightI]){
//         s = s.concat(s.substring(0, leftI), s.substring(leftI+1,s.length))
//       }
//       else if(s[leftI] === s[rightI-1]){
//         s = s.concat(s.substring(0, rightI), s.substring(rightI == s.length-1?s.length,s.length))
//       }
//     }
//   }
// }


// function gridChallenge(grid) {
//   // Write your code here
//   for(let i = 0; i < grid.length; i++) {
//     grid[i].sort()
//   }

//   for(let i = 0; i < grid.length-1; i++) {

//   }
// }


function countFamilyLogins(logins) {
  // Write your code here
  function rotateString(s, k) {
    let newString = ''
    for (let i = 0; i < s.length; i++) {
      let asciiChar = s[i].charCodeAt(0);
      let newAciichar = asciiChar + k
      if (asciiChar >= 97 && asciiChar <= 122) {
        if (newAciichar > 122) {
          newString = newString.concat(String.fromCodePoint((newAciichar % 122) + 96))
        }
        else {
          newString = newString.concat(String.fromCodePoint(newAciichar))
        }
      }
    }
    return newString
  }
  let total = 0
  for (const login of logins) {
    const rotatedL = rotateString(login, 1)
    for (let i = 0; i < logins.length; i++) {
      if (rotatedL === logins[i]) {
        ++total
      }
    }
  }
  return total
}


// console.log(countFamilyLogins(['corn', 'horn', 'dpso', 'eqtp', 'corn']))


function countPossibleSegments(k, weights) {
  // Write your code here
  let permutations = 0
  console.log(weights)
  for(let i = 0; i < weights.length; i++) {
    for(let j = i; j < weights.length; j++) {
      console.log(`Splicing array with lowerI ${i} and upperI ${j}`)
      const subArray = j + 1 === weights.length ? weights.slice(i) : weights.slice(i, j + 1)
      console.log(subArray)
      if((Math.max(...subArray) - Math.min(...subArray)) <= k){
        permutations += 1
      }
    }
  }
  return permutations
}


console.log(countPossibleSegments(3, [1,3,6]))