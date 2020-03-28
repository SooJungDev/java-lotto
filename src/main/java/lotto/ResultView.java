package lotto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultView {
    private static final String SPLIT_TEXT = ",";
    private static final int MIN_WIN_MATCH_COUNT = 3;

    private LottoNumber winningNumbers;
    private List<LottoNumber> purchaseLottoNumbers;
    private Map<Integer, WinningLotto> winningLottos;

    public ResultView(String inputText) {
        this(inputText, null);
    }

    public ResultView(String inputText, List<LottoNumber> purchaseLottoNumbers) {
        Set<Integer> numbers = splitWinningNumber(inputText);
        winningNumbers = new LottoNumber(numbers);
        this.purchaseLottoNumbers = purchaseLottoNumbers;
        this.winningLottos = getWinningLottos();
    }

    public Map<Integer, WinningLotto> getWinningLottos() {
        Map<Integer, WinningLotto> winningLottos = new HashMap<>();
        Set<Integer> winningNums = winningNumbers.getNumbers();
        for (int i = 0; i < purchaseLottoNumbers.size(); i++) {
            int matchCount = 0;
            Set<Integer> purchaseNumbers = purchaseLottoNumbers.get(i).getNumbers();
            matchCount = repeatByWinNumberSize(winningNums, matchCount, purchaseNumbers);

            addWinningLotto(winningLottos, matchCount);
        }
        return winningLottos;
    }

    private void addWinningLotto(Map<Integer, WinningLotto> winningLottos, int matchCount) {
        if (matchCount < MIN_WIN_MATCH_COUNT) {
            return;
        }
        if (!winningLottos.containsKey(matchCount)) {
            LottoWinningInfo lottoWinningInfo = LottoWinningInfo.valueOf(matchCount);
            winningLottos.put(matchCount, new WinningLotto(lottoWinningInfo.getWinningAmount(), 1));
            return;
        }

        WinningLotto winningLotto = winningLottos.get(matchCount);
        winningLotto.addCount();

    }

    public String printWinningResult() {
        String result = "";
        LottoWinningInfo[] values = LottoWinningInfo.values();
        for (LottoWinningInfo value : values) {
            int matchCount = value.getMatchCount();
            int resultCount = getResultCount(matchCount);
            result += matchCount + "개 일치(" + value.getWinningAmount() + "원)- " + resultCount + "개\n";
        }

        return result;
    }

    private int getResultCount(int matchCount) {
        int resultCount = 0;
        if (winningLottos.containsKey(matchCount)) {
            resultCount = winningLottos.get(matchCount).getCount();
        }
        return resultCount;
    }

    private Set<Integer> splitWinningNumber(String inputText) {
        return Arrays
                .stream(inputText.split(SPLIT_TEXT))
                .map(num -> Integer.parseInt(num))
                .collect(Collectors.toSet());
    }

    private int repeatByWinNumberSize(Set<Integer> winningNums, int matchCount, Set<Integer> purchaseNumbers) {
        for (Integer winningNum : winningNums) {
            matchCount = checkCotainsWinNumber(matchCount, purchaseNumbers, winningNum);
        }
        return matchCount;
    }

    private int checkCotainsWinNumber(int matchCount, Set<Integer> purchaseNumbers, Integer winningNum) {
        if (purchaseNumbers.contains(winningNum)) {
            ++matchCount;
        }
        return matchCount;
    }

}
