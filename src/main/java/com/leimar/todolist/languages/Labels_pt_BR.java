package com.leimar.todolist.languages;

import java.util.ListResourceBundle;

public class Labels_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
			{ "taskListWindowTitle", "Lista de Tarefas" },
			{ "editTaskWindowTitleAdd", "Adicionar Tarefa" },
			{ "editTaskWindowTitleEdit", "Editar Tarefa" },
			{ "addButton", "Adicionar" }, { "editButton", "Editar" },
			{ "toogleDoneUndone", "Marcar como feito" },
			{ "toogleDoneDone", "Marcar como não feito" },
			{ "removeButton", "Remover" },
			{ "choiceBoxUndone", "Mostrar tarefas não concluídas" },
			{ "choiceBoxDone", "Mostrar tarefas concluídas" },
			{ "choiceBoxAll", "Mostrar todas as tarefas" },
			{ "nameLabel", "Nome" },
			{ "descriptionLabel", "Descrição" },
			{ "startDateLabel", "Data de início" },
			{ "endDateLabel", "Data de término" },
			{ "cancelButton", "Cancelar" },
			{ "saveButton", "Salvar" },
			{ "taskNameErrorTitle", "Erro" },
			{ "taskNameErrorHeader", "O nome está vazio." },
			{ "taskNameErrorContext", "O nome não pode ficar vazio." }
		};
	}

}
