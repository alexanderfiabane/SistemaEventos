
/**
 * Mostra um dialogo de confirmação antes do redirecionamento,
 * com uma mensagem customizada.
 *
 * @param url       A url para onde o redirecionamento será efetuado se o usuário confirmar (apertar OK).
 * @param message   A mensagem, usualmente uma pergunta, para mostrar ao usuário.
 */
function confirmRedir(url, message) {
    var confirmou = window.confirm(message);
    if (confirmou) {
        location.href=url;
    }
}
