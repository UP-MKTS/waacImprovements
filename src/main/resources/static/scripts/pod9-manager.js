function changeDepartment (departmentId) {

    let date = document.getElementById('Pod9Date').value;

    fetch('api/pod9/change-department/' + departmentId+'/'+date,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {

            FillTabe(result);

        });
}

function FillTabe(result){
    let html = "";
    $('#wasteTypeCode option').remove();
    $('#wasteTypeName option').remove();

    if(result["wasteTypeDtos"]!=undefined) {
        for (let waste of result["wasteTypeDtos"]) {
            $("#wasteTypeCode").prepend("<option value=" + waste["id"] + ">" + waste["code"] + "</option>")
            $("#wasteTypeName").prepend("<option value=" + waste["id"] + ">" + waste["name"] + "</option>")
        }

        let dangerousPowAndClass = 1;
        for (let codeId of result["pod9DtoList"]) {
            if (codeId["dangerousPowName"] != null) {
                $("#dangerousPowAndClass").text(codeId["dangerousClassName"] + " / " + codeId["dangerousPowName"]);
                break;
            }
        }

        let code = 1;
        for (let codeId of result["pod9DtoList"]) {
            if (codeId["wasteId"] != null) {
                code = codeId["wasteId"];
                $("#activityKind").text(codeId["activityKindName"]);
                $("#wasteNorm").text(codeId["wasteNorm"]);
                break;
            }
        }
        $("#wasteTypeCode option[value=" + code + "]").prop('selected', true);
        $("#wasteTypeName option[value=" + code + "]").prop('selected', true);
        for (let pod9 = 0; pod9 < result["pod9DtoList"].length; pod9++) {
            html += "<tr id=\"'row_'" + result["pod9DtoList"]["id"] + "\">";
            html += "<td>" + (pod9 + 1) + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["transparentDate"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["wasteGenerate"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["countFromOther"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["nameOther"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["countFromPeople"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["countUsed"] + "</td>";
            html += "<td>" + result["pod9DtoList"][pod9]["countNeutralized"] + "</td>";
            if (result["pod9DtoList"][pod9]["wasteWeight"] != null) {
                html += "<td>" + result["pod9DtoList"][pod9]["wasteWeight"] + "</td>";
            } else {
                html += "<td>-</td>";
            }
            if (result["pod9DtoList"][pod9]["recipientOrganizationName"] != null) {
                html += "<td class=\"left\">" + result["pod9DtoList"][pod9]["recipientOrganizationName"] + "</td>";
            } else {
                html += "<td>-</td>";
            }
            if (result["pod9DtoList"][pod9]["goalName"] != null) {
                html += "<td>" + result["pod9DtoList"][pod9]["goalName"] + "</td>";
            } else {
                html += "<td>-</td>";
            }
            html += "<td>" + result["pod9DtoList"][pod9]["countKeeping"] + "</td>";
            html += "<td>";
            html += "<div>";
            if (result["pod9DtoList"][pod9]["goalName"] == null) {
                html += "<i class=\"fa fa-edit fa-lg border\"";
                html += "onClick=\"updatePod('" + result["pod9DtoList"][pod9]["id"] + "')\"></i>";
                html += "<i class=\"fa fa-copy fa-lg border\"";
                html += "onClick=\"duplicate('" + result["pod9DtoList"][pod9]["id"] + "','pod9')\"></i>";
                html += "<i class=\"fa fa-trash-o fa-lg border\"";
                html += "onClick=\"remove('" + result["pod9DtoList"][pod9]["id"] + "','pod9')\"></i>";
            }
            html += "</div>";
            html += "</td>";
            html += "</tr>";
        }
    }else {
        html+="<tr><td colspan=\"13\">Нет данных</td></tr>";
        $("#wasteTypeCode").prepend("<option>нет данных</option>");
        $("#wasteTypeName").prepend("<option>нет данных</option>");
        $("#dangerousPowAndClass").text("Нет данных");
        $("#wasteNorm").text("Нет данных");
        $("#activityKind").text("Нет данных");
    }

    $("#containerTable").html(html);
}

function downloadSved(){
    let idDepartment = $("#department").val()

    fetch('api/pod9/downloadSved/',{
        method:'GET'})
        .then(response => response.blob())
        .then(blob => {
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = "filename.xlsx";
            document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
            a.click();
            a.remove();  //afterwards we remove the element again
        });
}

function downloadPod9(catatogName)
{
    let idDepartment = $("#department").val()

    fetch('api/'+catatogName+'/download/'+idDepartment,{
        method:'GET'})
        .then(response => response.blob())
        .then(blob => {
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = "filename.xlsx";
            document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
            a.click();
            a.remove();  //afterwards we remove the element again
        });
}


function fillPod9Title(wasteTypeDtoList, wasteTypeId, dangerousPowAndClassName) {
    let wasteNameSelect = document.getElementById('wasteTypeName');
    let wasteCodeSelect = document.getElementById('wasteTypeCode');
    wasteNameSelect.options.length = 0;
    wasteCodeSelect.options.length = 0;
    let wasteNameOptions = wasteNameSelect.options;
    let wasteCodeOptions = wasteCodeSelect.options;
    let i = 0;
    let numEl = 0;
    for (let wasteType of wasteTypeDtoList) {
        wasteNameOptions[i] = new Option(wasteType.name, wasteType.id);
        wasteCodeOptions[i] = new Option(wasteType.code, wasteType.id);
        if (wasteType.id === Number(wasteTypeId)) {
            wasteNameOptions.selectedIndex = i;
            wasteCodeOptions.selectedIndex = i;
            numEl = i;
        }
        i++;
    }

    document.getElementById('dangerousPowAndClass').innerText = dangerousPowAndClassName;
    document.getElementById('wasteNorm').innerText = wasteTypeDtoList[numEl].wasteNorm;
    document.getElementById('activityKind').innerText = wasteTypeDtoList[numEl].activityKindName;
}

// Порядок следования столбцов определяется порядком следования полей в Pod9Dto.class
function fillTableData(pod9, tbody) {
    if(pod9.wasteDtoList.length === 0) {
        let row  = tbody.insertRow(0);
        let cell = row.insertCell(0);
        cell.setAttribute("colspan", 13);
        cell.innerText = "Нет данных";
        return;
    }

    for (let pod9Row of pod9.wasteDtoList) {
        let row  = tbody.insertRow(-1);
        let i = 0;
        row.insertCell(i).innerText = row.rowIndex - 1;
        for (let key in pod9Row) {
            if (key === 'id' || key.includes('Id') || key === 'wasteTypeDtoList') {
                continue;
            }

            if (typeof pod9Row[key] === "number" && pod9Row[key] === 0 ) {
                let cell = row.insertCell(++i);
                cell.innerText = pod9Row[key].toFixed(1);
                continue;
            }

            let cell = row.insertCell(++i);
            cell.innerText = pod9Row[key];

            setAttributes(pod9Row, key, row, cell);
        }

        fillActionsCell(row, ++i, pod9Row.ownWasteId, pod9Row.accompPasspId);
    }
}

async function savePod() {

    $("#wasteTypeId").removeAttr("disabled");
    $("#departmentId").removeAttr("disabled");
    let object = {};
    let formData = new FormData(document.forms.data);

    formData.forEach(function (value, key) {
            object[key] = convertFormData(key, value);
    });
    let json = JSON.stringify(object);
    let response = await fetch('api/pod9/', {
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

function updatePod(pod9Id)
{
    document.location.href = "#saveModal";
    document.getElementById('modalTitle').innerText = "Редактирование данных";
    fetch('api/pod9/' + pod9Id,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {
            $("#id").val(result["id"]);
            $("#departmentId").val(result["departmentId"]);
            $("#wasteTypeId").val(result["wasteTypeId"]);
            $("#countFromOther").val(result["countFromOther"]);
            $("#date").val(convetDate(result["date"]));
            $("#nameOther").val(result["nameOther"]);
            $("#wasteGenerate").val(result["wasteGenerate"]);
            $("#countFromPeople").val(result["countFromPeople"]);
            $("#countUsed").val(result["countUsed"]);
            $("#countNeutralized").val(result["countNeutralized"]);
        });
}

function fillActionsCell(row, i, pod9RowId, accompPasspId) {
    let cell = row.insertCell(i);

    if (accompPasspId == null) {
        let divActions = document.createElement('div');
        let iUpdate = document.createElement('i');
        iUpdate.classList.add('fa', 'fa-edit', 'fa-lg', 'border');
        iUpdate.setAttribute('onclick','showUpdate('+ pod9RowId + ', \'pod9\')');
        divActions.appendChild(iUpdate);

        let iDuplicate = document.createElement('i');
        iDuplicate.classList.add('fa', 'fa-copy', 'fa-lg', 'border');
        iDuplicate.setAttribute('onclick','duplicate('+ pod9RowId + ', \'pod9\')');
        divActions.appendChild(iDuplicate);

        let iRemove = document.createElement('i');
        iRemove.classList.add('fa', 'fa-trash-o', 'fa-lg', 'border');
        iRemove.setAttribute('onclick','remove('+ pod9RowId + ', \'pod9\')');
        divActions.appendChild(iRemove);

        cell.appendChild(divActions);
    }
}

function changeWasteType (wasteTypeId) {
    let departmentId = document.getElementById('department').value;
    let date = document.getElementById('Pod9Date').value;

    fetch('api/pod9/' + departmentId +'/' + wasteTypeId+'/'+date,{
        method: 'GET',
        headers: {'Content-Type': 'application/json; charset=utf-8'}})
        .then(response => response.json())
        .then(result => {
            FillTabe(result);
        });
}

function setCurrentValue() {
    let departmentId = document.getElementById('department').value;
    let wasteTypeId = document.getElementById('wasteTypeName').value;
    $("#wasteTypeId").attr("disabled", "disabled");
    $("#departmentId").attr("disabled", "disabled");

    document.getElementById('departmentId').value = departmentId;
    if (wasteTypeId !== 'null') {
        document.getElementById('wasteTypeId').value = wasteTypeId;
    }
}