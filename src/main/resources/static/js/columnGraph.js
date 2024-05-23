
// 데이터와 차트 옵션 설정
const data = {
    categories: ['' ],
    series: [
        {
            name: '방문자',
            data: [30]
        }
    ]
};

const options = {
    chart: {
        title: '조회수',
        width: 300,
        height: 400
    },
    yAxis: {
        title: '방문자 수'
    },
    xAxis: {
        title: ''
    },
    series: {
        stack: false
    }
};


// 컬럼 차트 생성
const chart = toastui.Chart.columnChart({
    el: document.getElementById('chart'),
    data: data,
    options: options
});