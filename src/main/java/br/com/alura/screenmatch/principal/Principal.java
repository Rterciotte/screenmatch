package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.services.ConsumoAPI;
import br.com.alura.screenmatch.services.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoAPI consumo = new ConsumoAPI();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=77bc2dca";

    private ConverteDados conversor = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {
        System.out.println("Digite o nome da série para a busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo
                .obterDados(ENDERECO + nomeSerie
                        .replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie
                    .replace(" ", "+") + "&season="+ i +  API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}
