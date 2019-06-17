// Data
let courbes = [];
let smoothie = new SmoothieChart({
    tooltip:true,
    grid: { strokeStyle:'rgb(125, 0, 0)', fillStyle:'rgb(60, 0, 0)',
        lineWidth: 1, millisPerLine: 250, verticalSections: 6, },
    labels: { fillStyle:'rgb(60, 0, 0)' },
});
let index = -1 ;
let colors  = [
    {strokeStyle:'rgb(0, 255, 0)', fillStyle:'rgba(0, 255, 0, 0.4)', lineWidth:3},
    {strokeStyle:'rgb(255, 0, 255)', fillStyle:'rgba(255, 0, 255, 0.4)', lineWidth:3},
    {strokeStyle:'rgb(0, 0, 255)', fillStyle:'rgba(0, 0, 255, 0.4)', lineWidth:3},
    {strokeStyle:'rgb(255, 0, 0)', fillStyle:'rgba(255, 0, 0, 0.4)', lineWidth:3},

] ;
smoothie.streamTo(document.getElementById("mycanvas"));

function onConnected(id) {
    if(!courbes[id])
    {
        courbes[id] = new TimeSeries();
        let color =   getOneRandomColor();
        smoothie.addTimeSeries(courbes[id],color);
       let connection = new EventSource("/streamTransactionsByCompany/" + id);
        connection.onmessage  =  function (response) {
            let transaction = JSON.parse(response.data);
            courbes[id].append(new Date().getTime(), transaction.price);
        }
    }
}

function getOneRandomColor() {
    index++;
    if(index > colors.length) index = 0;
    return colors[index];
}