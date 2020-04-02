package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResultViewTest {

    private static ResultView resultView = ResultView.getResultView();
    private WinningLottoInfo winningLottoInfo;
    private LottoTicket lottoTicket;

    @BeforeEach
    void setUp() {
        String input = "1,2,3,4,5,6";
        int bonusBall = 42;
        List<LottoNumbers> lottoNumbers = new ArrayList<>();
        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(1),
                                                                  new LottoNo(2),
                                                                  new LottoNo(3),
                                                                  new LottoNo(4),
                                                                  new LottoNo(5),
                                                                  new LottoNo(6))
                                                              .collect(Collectors.toSet())))); //1등

        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(1),
                                                                  new LottoNo(2),
                                                                  new LottoNo(3),
                                                                  new LottoNo(34),
                                                                  new LottoNo(35),
                                                                  new LottoNo(45))
                                                              .collect(Collectors.toSet()))));//5등

        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(2),
                                                                  new LottoNo(3),
                                                                  new LottoNo(4),
                                                                  new LottoNo(5),
                                                                  new LottoNo(35),
                                                                  new LottoNo(42))
                                                              .collect(Collectors.toSet())))); //4등

        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(2),
                                                                  new LottoNo(3),
                                                                  new LottoNo(4),
                                                                  new LottoNo(5),
                                                                  new LottoNo(6),
                                                                  new LottoNo(42))
                                                              .collect(Collectors.toSet()))));// 2등

        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(21),
                                                                  new LottoNo(22),
                                                                  new LottoNo(23),
                                                                  new LottoNo(24),
                                                                  new LottoNo(25),
                                                                  new LottoNo(26))
                                                              .collect(Collectors.toSet()))));//꽝

        lottoNumbers.add(new LottoNumbers(new HashSet<>(Stream.of(new LottoNo(2),
                                                                  new LottoNo(22),
                                                                  new LottoNo(23),
                                                                  new LottoNo(24),
                                                                  new LottoNo(25),
                                                                  new LottoNo(26))
                                                              .collect(Collectors.toSet()))));//꽝

        lottoTicket = new LottoTicket(lottoNumbers);
        winningLottoInfo = new WinningLottoInfo(bonusBall, input);

    }

    @DisplayName("당첨된 로또의 개수를 확인한다")
    @Test
    void winningResultSize() {
        Map<String, WinningLotto> winningLottos = resultView.getWinningLottos(winningLottoInfo, lottoTicket);

        assertThat(winningLottos).hasSize(4);
    }

    @DisplayName("당첨 내역을 출력한다")
    @Test
    void printWinningResult() {
        Map<String, WinningLotto> winningLottos = resultView.getWinningLottos(winningLottoInfo, lottoTicket);
        String result = resultView.printWinningResult(winningLottos);
        System.out.println(result);
        assertThat(result).isEqualTo("당첨통계\n"
                                     + "---------\n"
                                     + "3개 일치(5000원)- 1개\n"
                                     + "4개 일치(50000원)- 1개\n"
                                     + "5개 일치(1500000원)- 0개\n"
                                     + "5개 일치, 보너스 볼 일치(30000000원)- 1개\n"
                                     + "6개 일치(2000000000원)- 1개\n");
    }

    @DisplayName("총 구매금액을 계산한다")
    @Test
    void totalPurchaseAmount() {
        int result = resultView.totalPurchaseAmount(lottoTicket);
        assertThat(result).isEqualTo(6000);
    }

    @DisplayName("총 당첨금액을 계산한다")
    @Test
    void totalWinningAmount() {
        Map<String, WinningLotto> winningLottos = resultView.getWinningLottos(winningLottoInfo, lottoTicket);
        int result = resultView.totalWinningAmount(winningLottos);
        assertThat(result).isEqualTo(2030055000);
    }

    @DisplayName("총 수익률을 계산한다.")
    @Test
    void calculateRevenuePercent() {
        double result = resultView.calculateRevenuePercent(5000, 14000);
        assertThat(result).isEqualTo(0.35);
    }

    @DisplayName("구매한 로또티켓에 보너스 볼이 있는지 확인한다.")
    @Test
    void checkMatchBonusBall() {
        boolean match = resultView.checkMatchBonusBall(winningLottoInfo, new HashSet<>(Stream.of(new LottoNo(1),
                                                                                                 new LottoNo(2),
                                                                                                 new LottoNo(3),
                                                                                                 new LottoNo(4),
                                                                                                 new LottoNo(5),
                                                                                                 new LottoNo(42))
                                                                                             .collect(Collectors
                                                                                                              .toSet())));

        assertThat(match).isTrue();
    }
}
