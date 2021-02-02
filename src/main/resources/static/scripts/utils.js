function removeTbody() {
    let table = document.getElementById('data-view');
    table.getElementsByTagName('tbody')[0].remove();
    return table;
}

function pageReload() {
    document.location.href = "#close";
    document.location.reload();
}

function pageReloadJournal() {
    document.location.href = "#close";
    let year = $("#year").val();
    let month = $("#month").val();
    changeJournalByYearAndMonth(year,month);
}

function setAttributes(data, key, row, cell) {
    if (key === 'accompPasspNumber') {
        cell.setAttribute('tooltip-short', 'Дата СП: ' + data['accompPasspDate']);
        cell.setAttribute('flow', 'right');
        return cell;
    }

    if (key === 'recipientOrganizationName' || key === 'wasteName' || key === 'wasteNorm') {
        return cell.setAttribute('class', 'left');
    }
}