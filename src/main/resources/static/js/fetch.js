// 비동기 코드 작성
fetch('https://api.example.com/data')
    .then(response => response.json())
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.error('데이터를 가져오는 중 에러 발생:', error);
    });