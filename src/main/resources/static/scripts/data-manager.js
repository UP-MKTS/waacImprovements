async function save(catalogName) {
    let allInput = editingForm.getElementsByTagName("input");
    for (let input of allInput) {
        if (input.type === "checkbox") {
            input.value = input.checked;
        }
    }

    let object = {};
    let formData = new FormData(document.forms.data);
    let objectWast = {
        wasteId: 0 ,
        departmentId: 0,
        boxing: "",
        address: "",
    }
    let objectsWast;
    object["accompPasspWasteSaveDtos"] = [];
    let flag = 0
    formData.forEach(function (value, key) {
        if(key.includes("departmentInfo"))
        {
            let dep = "department-";
            let wast = "tableWasteId-";
            let wasteId = key.slice(key.indexOf(wast) + wast.length, key.indexOf("]")).toString();
            key = key.replace("[" + wast + wasteId + "]", "");
            let departmentId = key.slice(key.indexOf(dep) + dep.length, key.indexOf("]"));
            objectWast["departmentId"] = departmentId;
            objectWast["wasteId"] = wasteId;
            if (key.includes("boxing")) {
                objectWast["boxing"] = value;
            } else {
                objectWast["address"] = value;
            }
            flag +=1;
            if(flag === 2)
            {
                flag = 0;
                object["accompPasspWasteSaveDtos"].push(objectWast);
                objectWast = {
                    wasteId: 0 ,
                    departmentId: 0,
                    boxing: "",
                    address: ""
                }
            }
        }else
        {
            if(catalogName==="accomp-passp") {
                if (key != "departmentId" && key != "wasteTypeId")
                    object[key] = convertFormData(key, value);
            }else
            {
                object[key] = convertFormData(key, value);
            }
        }


    });


    // if(catalogName === "accomp-passp") {
    //     object.wasteTypeIdList = getWasteTypeList();
    //     object.departmentList = departmentList();
    // }
    let json = JSON.stringify(object);
    let response = await fetch('api/' + catalogName, {
        method: 'POST',
        headers: {'Content-Type': 'application/json; charset=utf-8'},
        body: json,
    });


    if (response.status === 203) {
        let result = await response.json();
        showErrors(result);
    } else {
        document.location.href = "#close";
        document.location.reload();
    }
}

function test()
{
    fetch('api/accomp-passp/'+1+"/2021",{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            let allDataElement = 0;
        })
}

function departmentList() {
    let divWasteTypes = document.getElementById('departmentList');
    let list = divWasteTypes.getElementsByTagName('div');

    let result = [];
    let i = 0;
    for (let wasteCode of list) {
        result[i++] = wasteCode.id.split('-')[1];
    }

    return result;
}

function getWasteTypeList() {
    let divWasteTypes = document.getElementById('wasteCodeList');
    let list = divWasteTypes.getElementsByTagName('div');

    let result = [];
    let i = 0;
    for (let wasteCode of list) {

        result[i++] = wasteCode.id.split('-')[1];
    }

    return result;
}


function downloadFile(id,catatogName) {
    if(catatogName === "pod9")
    {
        let id = $("#department").val();
    }
    try {
        fetch('api/'+catatogName+'/download/'+id,{
            method:'GET'})
            .then(response => response.blob())
            .then(blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = "filename.xls";
                document.body.appendChild(a);
                a.click();
                a.remove();
            });
    }
    catch (e) {
        logMyErrors(e);
    }
}

function downloadJournal(catatogName,year){

        fetch('api/'+catatogName+'/download/'+year,{
            method:'GET'})
            .then(response => response.blob())
            .then(blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = "filename.xlsx";
                document.body.appendChild(a);
                a.click();
                a.remove();
            });


}

function remove(id, catatogName) {
    let answer = confirm('Вы уверенны что хотите удалить данную запись??');
    if (answer) {
        fetch('api/' + catatogName + '/' + id,{
            method: 'DELETE',})
            .then(() => location.reload())
    }
}
function downloadCatalog(catalogName){
    fetch('api/'+catalogName+'/download',{
        method:'GET'})
        .then(response => response.blob())
        .then(blob => {
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = "filename.xls";
            document.body.appendChild(a);
            a.click();
            a.remove();
        });
}

function showUpdate(id, catatogName) {
    document.location.href = "#saveModal";
    document.getElementById('modalTitle').innerText = "Редактирование данных";

    fetch('api/' + catatogName + '/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            //'wasteTypeId', 'wasteCodeList'
            $("#id").val(result["id"]);
            $("#number").val(result["number"]);
            $("#accompPasspDate").val(convetDate(result["accompPasspDate"]));
            $("#carModel").val(result["carModel"]);
            $("#transportationDate").val(convetDate(result["transportationDate"]));
            $("#carNumber").val(result["carNumber"]);
            $("#driverFio").val(result["driverFio"]);
            showRecord(result["wasteTypeIdList"]);
            // showRecord('departmentId', result["departmentDtos"],'departmentList','department','id','shortName');
        })
}

function showUpdateCatalogs(id, catatogName){
    document.location.href = "#saveModal";
    document.getElementById('modalTitle').innerText = "Редактирование данных";

    fetch('api/' + catatogName + '/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            let allDataElement = editingForm.getElementsByClassName("data-el");
            for (let input of allDataElement) {
                for (let key in result) {
                    if (input.name === key && input.type !== "button" && input.type !== "date") {
                        input.value = result[key];
                        input.checked = result[key];
                        break;
                    }
                    if (input.name === key && input.type === "date") {
                        input.value = convetDate(result[key]);
                    }
                }
            }
        })
}

function showUpdateJournal(id) {
    document.location.href = "#WeightModal";
    document.getElementById('modalTitle').innerText = "Редактирование данных";

    fetch('api/accomp-passp/update/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            let html = " <thead>\n" +
                "                                <tr>\n" +
                "                                    <th>№</th>\n" +
                "                                    <th>№ СП</th>\n" +
                "                                    <th>Дата СП</th>\n" +
                "                                    <th>Подразделение</th>\n" +
                "                                    <th>Код отхода</th>\n" +
                "                                    <th>Получатель отходов</th>\n" +
                "                                    <th th:width=\"170px\">Передано на:</th>\n" +
                "                                    <th th:width=\"100px\">Количество</th>\n" +
                "                                </tr>\n" +
                "                                </thead>\n" +
                "                                    <tbody ><tr>";
            html+="<td>1</td>";
            html+= "<td>"+result["accompPasspDto"]["number"]+"</td>";
            html+="<td>"+result["accompPasspDto"]["accompPasspDate"]+"</td>";
            html+="<td>"+result["accompPasspWasteDto"]["department"]["shortName"]+"</td>";
            html+= "<td>"+result["accompPasspWasteDto"]["wasteTypeId"]["code"]+"</td>";
            html+= "<td>"+result["accompPasspDto"]["recipientOrganizationName"]+"</td>";
            html+= "<td>";
            html+=   "<select id=\"sel"+result["accompPasspWasteDto"]["id"]+"\" onchange=\"saveRow("+result["accompPasspWasteDto"]["id"]+")\" class=\"weight-select\">";
            for (let goal of result["goalDtos"]){
                if(result["accompPasspWasteDto"]["goal"]!=null) {
                    if (goal["id"] === result["accompPasspWasteDto"]["goal"]["id"]) {
                        html += "<option selected value=\"" + goal["id"] + "\">" + goal["name"] + "</option>";
                    } else {
                        html += "<option value=\"" + goal["id"] + "\">" + goal["name"] + "</option>";
                    }
                }else
                {
                    html += "<option value=\"" + goal["id"] + "\">" + goal["name"] + "</option>";
                }

            }

            html+=   "</select>";
            html+=  " </td>";
            html+= "<td>";
            html+=      "<input value="+result["accompPasspWasteDto"]["wasteWeight"]+" type=\"number\" class=\"inpNumber\" id=\"inp"+result["accompPasspWasteDto"]["id"]+"\" onchange=\"saveRow("+result["accompPasspWasteDto"]["id"]+")\">";
            html+=  "</td>";
            html+="</tr></tbody>";
            $("#APWeightData").html(html);
        })

}

function duplicate(id, catatogName) {
    fetch('api/' + catatogName + '/' + id,{
        method: 'PUT',})
        .then(() => location.reload())
}

function clearFields () {
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

function showErrors(result) {
    let span = document.getElementsByTagName('span');
    for (let i = 0; i < span.length; i++) {
        span[i].innerHTML = '';
    }
    for (var key in result) {
        document.getElementById(key+'Err').innerHTML = result[key];
    }
}

function convetDate(date) {
    let dateParts = date.split('.');
    return dateParts[2] + '-' + dateParts[1] + '-' + dateParts[0];
}

function convertFormData(key, value) {
    if (key.includes('Id') && !key.includes("departmentInfo")) {
        return Number(value);
    }
    if (key === "number" || key === "code"){
        return isInt(value);
    }
    if (key === "wasteWeight") {
        return isDouble(value);
    }
    if (value !== '' && (key === "orderDate" || key === "contractDate" || key === "accompPasspDate" || key === "transportationDate" || key === "date")) {
        return new Date(value).toLocaleDateString();
    }
    return value;
}

function isInt(value) {
    if(value.includes(',') || value.includes('.') ){
        return null;
    }
    return Number(value);
}

function isDouble(value) {
    if (isNaN(value) || value.includes(' ')) {
        return -0.1;
    }
    return value;
}

function infoOrg(selectId, divId) {
    let id = document.getElementById(selectId).value;
    fetch('api/organization/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            document.getElementById(divId).setAttribute('tooltip', result['address']);
        })
}

function infoContr(selectId, divId) {
    let id = document.getElementById(selectId).value;
    fetch('api/contract/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            let wasteDestination = '';
            if (result['wasteDestination'] !== null) {
                wasteDestination = ';\n' + result['wasteDestination'];
            }
            document.getElementById(divId).setAttribute('tooltip', result['organizationName'] + wasteDestination);
        })
}

function infoWT(selectId, divId, fieldView) {
    let id = document.getElementById(selectId).value;
    fetch('api/waste-type/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            document.getElementById(divId).setAttribute('tooltip', result[fieldView] +
               ';\n' + result['dangerousPowName'] + ' ' + result['dangerousClassName']);
        })
}

function infoDep(selectId, divId) {
    let id = $('#'+selectId).val();
    fetch('api/department/' + id,{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            document.getElementById(divId).setAttribute('tooltip-short', result['chiefPosition'] + ', ' + result['chiefFio']);
        })
}

function getNumber() {
    fetch('api/accomp-passp/next-number',{
        method: 'GET',})
        .then(response => response.json())
        .then(result => {
            document.getElementById('number').value = result;
            document.getElementById('accompPasspDate').value = convetDate(new Date().toLocaleDateString());
            document.getElementById('recipientOrganizationId').selectedIndex = 1;
        })
}
