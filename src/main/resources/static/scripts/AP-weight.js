function createWeightTable() {
    let table = document.getElementById('APWeightData');
    table.getElementsByTagName('tbody')[0].remove();
    let tbody = table.createTBody();

    fillWeightTable(tbody);
}

function fillWeightTable(tbody) {
    fetch('api/accomp-passp/weight',{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {

            for (let accompPassp of result) {
                for (let accompPasspWaste of accompPassp["wasteTypeIdList"]) {
                    if(accompPasspWaste["wasteWeight"]!=0.0)
                    {
                        continue;
                    }
                    let row  = tbody.insertRow(-1);
                    let cells =[];
                    for (let i=0 ; i<=10;i++)
                    {
                        cells.push(row.insertCell(i));

                    }
                    cells[0].innerText=row.rowIndex;
                    cells[1].innerText=accompPassp["number"];
                    cells[2].innerText=accompPassp["accompPasspDate"];
                    cells[3].innerText=accompPasspWaste["wasteTypeId"]["code"];
                    cells[4].innerText=accompPassp["recipientOrganizationName"];
                    let select = fillGoalSelect(accompPasspWaste["id"], accompPasspWaste["goal"]["id"]);
                    select.classList.add('weight-select');
                    cells[5].appendChild(select);
                    cells[6].innerHTML = '<input type="number"  class="inpNumber" id="inp' + accompPasspWaste["id"] + '" onchange="saveRow(' + accompPasspWaste["id"] +')"/>';
                }

            }

        });
}

function fillGoalSelect(id, goalId) {
    console.log(goalId);
    let select = document.createElement('select');
    select.setAttribute("id", "sel"+id)
    select.setAttribute("onchange", "saveRow(" + id + ")")
    let options = select.options;

    fetch('api/goal',{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {
            let i = 0;
            for (let goal of result) {
                options[i++] = new Option(goal.name, goal.id);
                if (goal.id === goalId) {
                    options.selectedIndex = i - 1;
                }
            }
    });
    return select;
}

// function downloadJournal(catatogName){
//     fetch('api/'+catatogName+'/download',{
//         method:'GET'})
//         .then(response => response.blob())
//         .then(blob => {
//             var url = window.URL.createObjectURL(blob);
//             var a = document.createElement('a');
//             a.href = url;
//             a.download = "filename.xlsx";
//             document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
//             a.click();
//             a.remove();  //afterwards we remove the element again
//         });
// }

function saveRow(id) {
    let weight = document.getElementById('inp' + id);
    let goal = document.getElementById('sel'+id);
    let weightValue = weight.value;
    if (weightValue === '') {
        weightValue = -1;
    }

    fetch('api/accomp-passp/weight/' + id + '/' + goal.value + '/' + weightValue,{
        method: 'POST'})
}