function changeJournalByYearAndMonth(year, month) {
    let table = removeTbody();
    createNewTbody(table, year, month);
}

function createNewTbody(table, year, month) {
    let tbody = table.createTBody();
    fetch('api/accomp-passp-journal/' + year + '/' + month,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {

            for (let accompPassp of result) {
                for (let accompPasspWaste of accompPassp["accompPasspWasteDtoList"]) {

                    let row  = tbody.insertRow(-1);
                    let cells =[];
                    for (let i=0 ; i<=11;i++)
                    {
                        cells.push(row.insertCell(i));

                    }
                    cells[0].innerText=row.rowIndex;
                    cells[1].innerText=accompPassp["accompPasspNumber"];
                    cells[1].setAttribute='tooltip-short', 'Дата СП: ' + accompPassp['accompPasspDate'];
                    cells[1].setAttribute='flow', 'right';
                    cells[2].innerText=accompPassp["accompPasspDate"];
                    cells[3].innerText=accompPasspWaste["department"]["shortName"];
                    cells[4].innerText=accompPassp["contractNumber"];
                    cells[5].innerText=accompPassp["contractDate"];
                    cells[6].innerText=accompPassp["recipientOrganizationName"];
                    cells[7].innerText=accompPasspWaste["wasteTypeId"]["code"];
                    cells[8].innerText=accompPasspWaste["wasteTypeId"]["dangerousClassName"];
                    if(accompPasspWaste["goal"]!=null) {
                        cells[9].innerText = accompPasspWaste["goal"]["name"];
                    }
                    else
                    {
                        cells[9].innerText = "";
                    }
                    cells[10].innerText=accompPasspWaste["wasteWeight"];
                    cells[11].innerHTML = "<div id=\"parent\" ><i class=\"fa fa-edit fa-lg border\" onclick=\"showUpdateJournal("+accompPasspWaste["id"]+")\"></i></div>"


                    //setAttributes(accompPassp, key, row, cell);
                }

            }
        });
}