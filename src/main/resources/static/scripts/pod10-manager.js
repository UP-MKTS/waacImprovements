function changeDate () {
    let table = removeTbody();
    let tbody = table.createTBody();
    let date = document.getElementById('Pod10Date').value;

    fetch('api/pod10/change-date/' + date,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {
            fillTableData(result, tbody);
        });
}

// Порядок следования столбцов определяется порядком следования полей в Pod10Dto.class
function fillTableData(pod10, tbody) {
    if(pod10["dtoList"].length === 0) {
        let row  = tbody.insertRow(0);
        let cell = row.insertCell(0);
        cell.setAttribute("colspan", 19);
        cell.innerText = "Нет данных";
        return;
    }

    for (let pod10Row of pod10["dtoList"]) {
        let row  = tbody.insertRow(-1);
        let i = 0;
        row.insertCell(i).innerText = row.rowIndex - 1;
        for (let key in pod10Row) {

            let cell;

            if ( key === 'date') {
                row.insertCell(++i);
                continue;
            }

            if(key === "transferredUsed" ||
                key ==="transferredNeutralized" ||
                key ==="transferredStored" ||
                key ==="transferredBuried"){
                continue;
            }
            if(key === "goalsPod10Dtos"){
                cell = row.insertCell(++i);
                cell.innerText = pod10Row["transferredUsed"];
                cell = row.insertCell(++i);
                cell.innerText = pod10Row["transferredNeutralized"];
                cell = row.insertCell(++i);
                cell.innerText = pod10Row["transferredStored"];
                cell = row.insertCell(++i);
                cell.innerText = pod10Row["transferredBuried"];
                continue;
            }


            cell = row.insertCell(++i);
            cell.innerText = pod10Row[key];
        }
    }
}