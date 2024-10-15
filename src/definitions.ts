export interface NoiseMeterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
