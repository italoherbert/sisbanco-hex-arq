package italo.sisbanco.core.exception;

import italo.sisbanco.error.ErrorException;

public class BusinessException extends ErrorException {

    public final static String USERNAME_EXISTS = "O nome de usuário informado já existe registrado.";
    public final static String EMAIL_EXISTS = "O email informado já existe registrado.";
    public final static String USER_NOT_FOUND = "Usuário não encontrado.";

    public final static String ACCOUNT_NOT_FOUND = "Conta não encontrada.";
    public final static String SOURCE_ACCOUNT_NOT_FOUND = "Conta de origem não encontrada.";
    public final static String DEST_ACCOUNT_NOT_FOUND = "Conta de destino não encontrada.";
    public final static String INSUFFICIENT_BALANCE = "Saldo insuficiente.";
    public final static String EQUALS_SOURCE_AND_DEST_ACCOUNTS = "As contas de origem e destino não podem ser a mesma.";
    public final static String NEGATIVE_DEPOSIT = "Tentativa de depositar valor negativo.";
    public final static String NEGATIVE_CASH = "Tentativa de sacar valor negativo.";
    public final static String NEGATIVE_TRANSFER_VALUE = "Tentativa de transferir valor negativo.";

    public BusinessException(String errorMSG, String... params) {
        super(errorMSG, params);
    }
    
}
