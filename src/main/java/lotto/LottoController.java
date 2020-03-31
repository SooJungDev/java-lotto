package lotto;

public class LottoController {

    private static ResultView resultView = new ResultView();

    public static void main(String[] args) {
        InputView inputView = new InputView();
        System.out.println(inputView.purchaseLottoTicketInfo());

        LottoGenerator lottoGenerator = new LottoGenerator(inputView.getAutomaticCount(),inputView.getManualCount());
        lottoGenerator.createLottoNumbersByPurchaseCount();
        LottoTicket lottoTicket = lottoGenerator.getLottoTicket();

        String printPurchaseLottoNumbers = resultView.printPurchaseLottoNumbers(lottoTicket);
        System.out.println(printPurchaseLottoNumbers);

        resultView = new ResultView(lottoTicket);
        System.out.println(resultView.printWinningResult());
        System.out.println(resultView.printRevenuePercent());

    }
}
