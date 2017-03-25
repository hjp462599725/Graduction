/*清除方法*/
function clearRow(grid) {
	if (grid.getSelectedRows().length == 0) {
		// var data = grid.getData();
		var data = grid.rows;
		if (data.length > 0) {
			var row = data[data.length - 1];
			// alert(row.__id);
			// 获取删除哪一行数据
			var rowData = grid.getRow(row.__id);
			grid.deleteRow(rowData);
		}
	} else {
		grid.deleteSelectedRow();
	}
}