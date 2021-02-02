var departmentsList;
$(document).ready(function() {
    fetch('api/department',{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            departmentsList = result;
        })
});

function showDetails(rowNum, id) {

    let tbody = document.getElementById('data-view').getElementsByTagName('tbody')[0];
    let detailsCaret = tbody.rows[rowNum - 1].cells[0].getElementsByTagName('i')[0];

    if (detailsCaret.classList.contains('fa-caret-right')) {
        detailsCaret.classList.replace('fa-caret-right', 'fa-caret-down');
        let row = tbody.insertRow(rowNum);

        let detailCell = row.insertCell(0);
        detailCell.setAttribute('colSpan', '10');
        detailCell.appendChild(createDetailTable(id));
    } else {
        detailsCaret.classList.replace('fa-caret-down', 'fa-caret-right');
        tbody.deleteRow(rowNum);
    }
}

function createDetailTable(id) {
    let table = document.createElement('table');
    table.classList.add('details-view');
    let thead = table.createTHead();
    let row0  = thead.insertRow(0);
    let tbody = table.createTBody();
    let row1  = tbody.insertRow(0);

    let detailTableHeader = {
        boxing: 'Упаковка',
        address:'Адрес',
        driverFio: 'ФИО водителя',
        carNumber: 'Номер автомобиля',
        carModel: 'Марка автомобиля',
        wasteDangerousClassName: 'Класс опасности',
        wasteDangerousPowName: 'Степень опасности',
        wasteName: 'Отход',
        department: 'Подразделение',
        transportationDate: 'Дата перевозки',
        contractDate: 'Дата договора',
        contractNumber: 'Номер договора',
    };

    // Порядок следования столбцов определяется порядком следования полей в AccompPasspDetailsDto.class
    fillDetailsTable(id, detailTableHeader, row0, tbody);

    return table
}

function fillDetailsTable(id, detailTableHeader, row0, tbody) {
    fetch('api/accomp-passp/details/' + id,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {
            let i = 0;
            for (let key in detailTableHeader){
                row0.insertCell(i).innerText = detailTableHeader[key];
            }
            i = -1;
            for(let value of result["accompPasspWasteDtoList"])
            {
                row0 = tbody.insertRow(0);
                row0.insertCell(i).innerText = result["contractNumber"];
                row0.insertCell(i).innerText = result["contractDate"];
                row0.insertCell(i).innerText = result["transportationDate"];
                row0.insertCell(i).innerText = value["department"]["shortName"];
                row0.insertCell(i).innerText = value["wasteTypeId"]["code"];
                row0.insertCell(i).innerText = value["wasteTypeId"]["dangerousPowName"];
                row0.insertCell(i).innerText = value["wasteTypeId"]["dangerousClassName"];
                row0.insertCell(i).innerText = result["carModel"];
                row0.insertCell(i).innerText = result["carNumber"];
                row0.insertCell(i).innerText = result["driverFio"];
                row0.insertCell(i).innerText = value["address"];
                row0.insertCell(i).innerText = value["boxing"];

            }
        });
}

let idRec = 0;

function clearSave(){
    let tables = document.getElementById("accompPasspInfo");
    $(tables).empty();
    document.getElementById('modalTitle').innerText = "Добавление новой записи";
    let allInput = editingForm.getElementsByTagName("input");
    for (let input of allInput) {
        if (input.type !== "button") {
            input.value = '';
            input.checked = false;
        }
    }
    let allSpan = editingForm.getElementsByTagName("span");
    for (let span of allSpan) {
        span.innerHTML = '';
    }

    let allSelect = editingForm.getElementsByTagName("select");
    for (let select of allSelect) {
        select.selectedIndex = 0;
    }

    let allTextarea = editingForm.getElementsByTagName("textarea");
    for (let textarea of allTextarea) {
        textarea.value = '';
    }
}

function addRecordDepatrment(selectName, tableId) {
    let selectValue = document.getElementById(selectName);
    let index = $(selectValue).val();
    let table = document.getElementById(tableId);
    let idName = tableId+"-"+"department-"+index;
    var tr;
    if (document.getElementById(idName) === null){
        tr = table.insertRow(-1);
        $(tr).attr('id', idName);
    } else {
        alert('Запись "' + selectValue.options[selectValue.selectedIndex].text + '" уже добавлена.');
        return;
    }
    let nameVal = $('#'+selectName+" option:selected").text();
    let widthFirst = 250;

    let cell = tr.insertCell();

    cell = tr.insertCell();
    let div = document.createElement('div')
    $(div).addClass(  "addRecord");
    let html = nameVal+"<i class=\"fa fa-close\" onclick=\"removeRecord('"+idName+"')\"></i>"
    $(div).append(html);
    $(cell).append(div);

    cell = tr.insertCell();
    cell.textContent = "Упаковка:";
    $(cell).addClass('right');
    cell = tr.insertCell();
    html = "<input type=\"text\" class=\"data-el\" name=\"departmentInfo["+tableId+"][department-"+index+"][boxing]\" id=\"boxing-"+idName+"\" value=\"б/т\">";
    // html = "<input type=\"text\" class=\"data-el\" name=\"boxing-"+idName+"\" id=\"boxing-"+idName+"\" value=\"б/т\">"
    $(cell).prepend(html);
    cell = tr.insertCell();
    cell.textContent = "Адрес:";
    $(cell).addClass('right');
    cell = tr.insertCell();
    html = "<input type=\"text\" class=\"data-el\" name=\"departmentInfo["+tableId+"][department-"+index+"][address]\" id=\"address-"+idName+"\" value=\"б/т\">";
    // html = "<input type=\"text\" class=\"data-el\" name=\"address-"+idName+"\" id=\"address-"+idName+"\" value=\"б/т\">"
    $(cell).prepend(html);

    for (let row of table.rows)
    {
        for (let currentCell of row.cells){
            $(currentCell).css('width', widthFirst);
        }
    }
}

function addRecord(selectName) {
    let selectValue = document.getElementById(selectName);
    let index = $(selectValue).val();
    let table = document.createElement('table');
    let tr = table.insertRow(-1);
    let idName = "tableWasteId-"+index;
    let nameVal = $('#'+selectName+" option:selected").text();
    let widthFirst = 250;
    let widthSecond = 250;
    if (document.getElementById(idName) === null){
        $('#accompPasspInfo').append(table);
    } else {
        alert('Запись "' + selectValue.options[selectValue.selectedIndex].text + '" уже добавлена.');
        return;
    }

    $(table).addClass(  "data-editing");
    $(table).attr('id',idName);

    let cell = tr.insertCell();
    cell.textContent = "Код отхода:";
    $(cell).addClass('right');

    cell = tr.insertCell();
    let div = document.createElement('div')
    $(div).addClass(  "addRecord");
    let html = nameVal+"<i class=\"fa fa-close\" onclick=\"removeRecord('"+idName+"')\"></i>"
    $(div).append(html);
    $(cell).append(div);

    cell = tr.insertCell();
    cell.textContent = "Подразделение:";
    $(cell).addClass('right');

    cell = tr.insertCell();
    let select = document.createElement('select')
    $(select).addClass(  "data-el");
    $(select).attr('id',  "department-"+index);
    for (let depatrment of departmentsList){
        $(select).prepend("<option value=\""+depatrment["id"]+"\" >"+depatrment['shortName']+"</option>");
    }
    $(cell).append(select);

    cell = tr.insertCell();

    let divInfo = document.createElement('div');
    $(divInfo).addClass(  "info-container");
    $(divInfo).attr('id', "departmentInfo-"+index);
    $(divInfo).css('flow','left');

    $(divInfo).attr('onmouseover', "infoDep('"+$(select).attr('id')+"','"+ $(divInfo).attr('id')+"')");
    let infoPlus = document.createElement('i');
    $(infoPlus).addClass(  "fa fa-plus-circle fa-lg green");
    $(infoPlus).attr('onclick',  "addRecordDepatrment('"+$(select).attr("id")+"', '"+ $(table).attr("id")+"')");
    let htmlInfo = document.createElement('i');
    $(htmlInfo).addClass(  "fa fa-info-circle fa-lg gray");
    $(divInfo).append(infoPlus,htmlInfo);

    $(cell).append(divInfo);
    cell = tr.insertCell();
    for (let row of table.rows)
    {
        for (let currentCell of row.cells){
            $(currentCell).css('width', widthFirst);
        }
    }

}

function showRecord(value) {
    let tabele = document.getElementById(accompPasspInfo);

    let wastes = [];
    let table = "";
    for (let temp of value) {
        if(wastes.indexOf("tableWasteId-"+temp["wasteTypeId"]["id"])==-1){
            table = addRecordShow(temp["wasteTypeId"]["id"],temp["wasteTypeId"]["code"])
            wastes.push(table);
        }else
        {
            table = wastes[wastes.indexOf("tableWasteId-"+temp["wasteTypeId"]["id"])];
        }
        addRecordDepatrmentShow(temp["department"]["id"],temp["department"]["shortName"],table,temp)
    }

}

function addRecordDepatrmentShow(departmentId, departmentName, tableId,wasteValue) {
    let index = departmentId;
    let table = document.getElementById(tableId);
    let idName = tableId+"-"+"department-"+index;
    var tr;
    tr = table.insertRow(-1);
    $(tr).attr('id', idName);
    let nameVal = departmentName;
    let widthFirst = 250;

    let cell = tr.insertCell();

    cell = tr.insertCell();
    let div = document.createElement('div')
    $(div).addClass(  "addRecord");
    let html = nameVal+"<i class=\"fa fa-close\" onclick=\"removeRecord('"+idName+"')\"></i>"
    $(div).append(html);
    $(cell).append(div);

    cell = tr.insertCell();
    cell.textContent = "Упаковка:";
    $(cell).addClass('right');
    cell = tr.insertCell();
    html = "<input type=\"text\" class=\"data-el\" name=\"departmentInfo["+tableId+"][department-"+index+"][boxing]\" id=\"boxing-"+idName+"\" value=\""+wasteValue["boxing"]+"\">";
    // html = "<input type=\"text\" class=\"data-el\" name=\"boxing-"+idName+"\" id=\"boxing-"+idName+"\" value=\"б/т\">"
    $(cell).prepend(html);
    cell = tr.insertCell();
    cell.textContent = "Адрес:";
    $(cell).addClass('right');
    cell = tr.insertCell();
    html = "<input type=\"text\" class=\"data-el\" name=\"departmentInfo["+tableId+"][department-"+index+"][address]\" id=\"address-"+idName+"\" value=\""+wasteValue["address"]+"\">";
    // html = "<input type=\"text\" class=\"data-el\" name=\"address-"+idName+"\" id=\"address-"+idName+"\" value=\"б/т\">"
    $(cell).prepend(html);

    for (let row of table.rows)
    {
        for (let currentCell of row.cells){
            $(currentCell).css('width', widthFirst);
        }
    }
}

function addRecordShow(wasteId,wasteName) {
    let index = wasteId;
    let table = document.createElement('table');
    let tr = table.insertRow(-1);
    let idName = "tableWasteId-"+index;
    let nameVal = wasteName;
    let widthFirst = 250;
    let widthSecond = 250;
    $('#accompPasspInfo').append(table);
    $(table).addClass(  "data-editing");
    $(table).attr('id',idName);

    let cell = tr.insertCell();
    cell.textContent = "Код отхода:";
    $(cell).addClass('right');

    cell = tr.insertCell();
    let div = document.createElement('div')
    $(div).addClass(  "addRecord");
    let html = nameVal+"<i class=\"fa fa-close\" onclick=\"removeRecord('"+idName+"')\"></i>"
    $(div).append(html);
    $(cell).append(div);

    cell = tr.insertCell();
    cell.textContent = "Подразделение:";
    $(cell).addClass('right');

    cell = tr.insertCell();
    let select = document.createElement('select')
    $(select).addClass(  "data-el");
    $(select).attr('id',  "department-"+index);
    for (let depatrment of departmentsList){
        $(select).prepend("<option value=\""+depatrment["id"]+"\" >"+depatrment['shortName']+"</option>");
    }
    $(cell).append(select);

    cell = tr.insertCell();

    let divInfo = document.createElement('div');
    $(divInfo).addClass(  "info-container");
    $(divInfo).attr('id', "departmentInfo-"+index);
    $(divInfo).css('flow','left');

    $(divInfo).attr('onmouseover', "infoDep('"+$(select).attr('id')+"','"+ $(divInfo).attr('id')+"')");
    let infoPlus = document.createElement('i');
    $(infoPlus).addClass(  "fa fa-plus-circle fa-lg green");
    $(infoPlus).attr('onclick',  "addRecordDepatrment('"+$(select).attr("id")+"', '"+ $(table).attr("id")+"')");
    let htmlInfo = document.createElement('i');
    $(htmlInfo).addClass(  "fa fa-info-circle fa-lg gray");
    $(divInfo).append(infoPlus,htmlInfo);

    $(cell).append(divInfo);
    cell = tr.insertCell();
    for (let row of table.rows)
    {
        for (let currentCell of row.cells){
            $(currentCell).css('width', widthFirst);
        }
    }

    return $(table).attr("id");

}


function removeRecord(idName) {

    $('#'+idName).remove();
}