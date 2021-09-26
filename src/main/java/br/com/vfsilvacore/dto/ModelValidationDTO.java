package br.com.vfsilvacore.dto;

public abstract class ModelValidationDTO<MODEL> implements Dto {

    public abstract MODEL toModel();
}
